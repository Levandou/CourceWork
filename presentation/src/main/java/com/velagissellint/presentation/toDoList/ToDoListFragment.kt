package com.velagissellint.presentation.toDoList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.velagissellint.presentation.R
import com.velagissellint.presentation.ViewModelFactory
import com.velagissellint.presentation.containersDi.ContainerAppContainer
import com.velagissellint.presentation.convertDateForUser
import com.velagissellint.presentation.toDoList.adapters.ToDoListAdapter
import com.velagissellint.presentation.toDoList.adapters.ToDoListLoadStateAdapter
import java.util.*
import javax.inject.Inject

class ToDoListFragment : Fragment() {
    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var toDoListViewModel: ToDoListViewModel

    lateinit var calendarView: CalendarView
    lateinit var rv: RecyclerView
    lateinit var calendarFrame: FrameLayout
    private lateinit var navController: NavController
    private lateinit var adapter: ToDoListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as ContainerAppContainer).appContainer()
            ?.plusToDoListComponent()?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = NavHostFragment.findNavController(this)
        toDoListViewModel = ViewModelProvider(this, factory)
            .get(ToDoListViewModel::class.java)
        return inflater.inflate(R.layout.fragment_to_do_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendarFrame = view.findViewById(R.id.fl_calendar_filter)
        calendarView = view.findViewById(R.id.calendarFilter)
        setHasOptionsMenu(true)
        setupRecyclerView(view)
        observeToDoListPaging()
    }

    private fun setupRecyclerView(view: View) {
        rv = view.findViewById(R.id.rv_to_do)
        adapter = ToDoListAdapter()
        rv.adapter = adapter
        rv.adapter = adapter.withLoadStateFooter(ToDoListLoadStateAdapter { adapter.retry() })
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                toDoListViewModel.deleteFromDb(
                    toDoListViewModel.toDoList[viewHolder.bindingAdapterPosition]
                )
                adapter.refresh()
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rv)
    }

    private fun observeToDoListPaging() {
        toDoListViewModel.toDoListPaging.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main, menu)
    }

    private fun clickOnDay(item: MenuItem) {
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            calendarFrame.isVisible = false
            rv.isVisible = true
            val convertedData = convertDateForUser(dayOfMonth, month, year)
            toDoListViewModel.loadToDoList(convertedData)
            Calendar.getInstance().apply {
                if (get(Calendar.YEAR) == year && get(Calendar.MONTH) == month)
                    when (dayOfMonth) {
                        get(Calendar.DAY_OF_MONTH) -> item.title = "Сегодня"
                        get(Calendar.DAY_OF_MONTH) + 1 -> item.title = "Завтра"
                        get(Calendar.DAY_OF_MONTH) - 1 -> item.title = "Вчера"
                        else -> item.title = convertedData
                    }
                else
                    item.title = convertedData
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.dayFilter -> {
                calendarFrame.isVisible = true
                rv.isVisible = false
                clickOnDay(item)
            }
            R.id.add -> navController.navigate(R.id.action_toDoListFragment_to_addItemFragment2)
        }
        return super.onOptionsItemSelected(item)
    }
}