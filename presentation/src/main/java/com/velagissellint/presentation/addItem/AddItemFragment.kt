package com.velagissellint.presentation.addItem

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.velagissellint.domain.models.Case
import com.velagissellint.presentation.MainActivity
import com.velagissellint.presentation.R
import com.velagissellint.presentation.ViewModelFactory
import com.velagissellint.presentation.containersDi.ContainerAppContainer
import com.velagissellint.presentation.convertDateForUser
import com.velagissellint.presentation.databinding.FragmentAddItemBinding
import java.util.*
import javax.inject.Inject

class AddItemFragment : Fragment() {
    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var addItemViewModel: AddItemViewModel

    private var _binding: FragmentAddItemBinding? = null
    private val binding: FragmentAddItemBinding
        get() = _binding ?: throw RuntimeException("FragmentAddItemBinding == null")
    private lateinit var navController: NavController
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as ContainerAppContainer).appContainer()?.plusAddItemComponent()
            ?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        addItemViewModel = ViewModelProvider(this, factory).get(AddItemViewModel::class.java)
        backPressed()
        navController = NavHostFragment.findNavController(this)
        _binding = FragmentAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.calendarView.minDate=Calendar.getInstance().timeInMillis
        configureSpinner(binding.spinnerType)
        binding.calendar.setOnClickListener {
            binding.flCalendar.isVisible = true
            binding.llAdd.isVisible = false
        }
        clickOnDay()
        clickOnAdd()
    }

    private fun backPressed() {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)
    }

    private fun configureSpinner(spinner: Spinner) {
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            activity?.applicationContext!!,
            R.array.types,
            R.layout.color_spinner_layout
        )
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout)
        spinner.adapter = adapter
    }

    private fun clickOnDay() {
        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            binding.flCalendar.isVisible = false
            binding.llAdd.isVisible = true
            binding.tvDate.text = convertDateForUser(dayOfMonth, month, year)
        }
    }

    private fun clickOnAdd() {
        binding.btAdd.setOnClickListener {
            addItemViewModel.addToDb(
                Case(
                    type = binding.spinnerType.selectedItem.toString(),
                    description = binding.etDescription.text.toString(),
                    date = binding.tvDate.text.toString()
                )
            )
            navController.navigate(R.id.action_addItemFragment_to_toDoListFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}