package com.velagissellint.presentation.toDoList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
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
       // backPressed()
        return inflater.inflate(R.layout.fragment_to_do_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        addToDb()
        setHasOptionsMenu(true)
        setupRecyclerView(view)
        observeToDoListPaging()
    }

//    private fun addToDb(){
//        toDoListViewModel.addItemToDb(Case(
//            id = 3,
//            type = "НУЖНО ВСТРЕТИТЬСЯ",
//            description = "ksdadkaskdksasdkkdskskksakldaskldaskldsksksdflsfllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllldasadsasddassddsaasdllllllll",
//            date = "11.07.2005"
//        ))
//    }

    private fun setupRecyclerView(view: View) {
        val rv = view.findViewById<RecyclerView>(R.id.rv_to_do)
        adapter = ToDoListAdapter()
        rv.adapter = adapter
    }

    private fun observeToDoListPaging() {
        toDoListViewModel.toDoListPaging.observe(viewLifecycleOwner, {
            adapter.submitData(lifecycle, it)
        })
    }

//    private fun backPressed() {
//        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                findNavController().popBackStack()
//            }
//        }
//        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
            inflater.inflate(R.menu.main, menu)
    }
}