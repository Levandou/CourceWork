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
import androidx.recyclerview.widget.RecyclerView
import com.velagissellint.presentation.R
import com.velagissellint.presentation.ViewModelFactory
import com.velagissellint.presentation.containersDi.ContainerAppContainer
import com.velagissellint.presentation.toDoList.adapters.ToDoListAdapter
import javax.inject.Inject


class ToDoListFragment : Fragment() {
    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var toDoListViewModel: ToDoListViewModel

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
        setHasOptionsMenu(true)
        setupRecyclerView(view)
        observeToDoListPaging()
    }

    private fun setupRecyclerView(view: View) {
         rv = view.findViewById(R.id.rv_to_do)
        adapter = ToDoListAdapter()
        rv.adapter = adapter
    }

    private fun observeToDoListPaging() {
        toDoListViewModel.toDoListPaging.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main, menu)
//        val positionOfMenuItem = 0 //or any other postion
//
//        val item = menu.getItem(positionOfMenuItem)
//        val s: SpannableString = SpannableString(item.title)
//
//        s.setSpan(AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, s.length, 0)
//
//        item.title = s
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.dayFilter -> {
                calendarFrame.isVisible=true
                rv.isVisible=false

            }
            R.id.add -> navController.navigate(R.id.action_toDoListFragment_to_addItemFragment2)
        }
        return super.onOptionsItemSelected(item)
    }
}