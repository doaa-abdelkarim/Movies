package com.ragabz.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.example.movies.R

/**
 * Base Databinding fragment
 */
abstract class BaseDBFragment<DB : ViewDataBinding, VM : ViewModel>(
    @LayoutRes val layoutId: Int
) : Fragment() {

    protected abstract val viewModel: VM

    private var _binding: DB? = null
    protected val binding: DB
        get() = _binding!!

    private var loader: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(layoutInflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loader = view.findViewById<ConstraintLayout>(R.id.loader)
        initViews()
        observeState()
        listenToEvents()
    }

    open fun initViews() {}

    open fun attachListeners() {}

    open fun observeState() {}

    open fun listenToEvents() {}

    protected fun onInProgress() {
        loader?.visibility = View.VISIBLE
    }

    protected open fun onRequestSucceeded() {
        onRequestCompleted()
    }

    protected open fun onRequestFailed(error: Throwable) {
        onRequestCompleted()
        Toast
            .makeText(
                context,
                error.localizedMessage ?: getString(
                    R.string.unknown_error
                ),
                Toast.LENGTH_LONG
            )
            .show()
    }

    private fun onRequestCompleted() {
        loader?.visibility = View.GONE
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

private typealias FragmentViewBindingInflater<VB> = (
    inflater: LayoutInflater,
    parent: ViewGroup?,
    attachToParent: Boolean
) -> VB

/**
 * Base Viewbinding fragment
 */
abstract class BaseVBFragment<VB : ViewBinding, VM : ViewModel> constructor(
    private val viewBindingInflater: FragmentViewBindingInflater<VB>
) : Fragment() {
    protected abstract val viewModel: VM

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding!!

    private var loader: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = viewBindingInflater.invoke(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loader = view.findViewById<ConstraintLayout>(R.id.loader)
        initViews()
        observeState()
        listenToEvents()
    }

    open fun initViews() {}

    open fun observeState() {}

    open fun listenToEvents() {}

    protected fun onInProgress() {
        loader?.visibility = View.VISIBLE
    }

    protected fun onRequestSucceeded() {
        onRequestCompleted()
    }

    protected fun onRequestFailed(error: Throwable) {
        onRequestCompleted()
        Toast
            .makeText(
                context,
                error.localizedMessage ?: getString(
                    R.string.unknown_error
                ),
                Toast.LENGTH_LONG
            )
            .show()
    }

    protected fun onRequestCompleted() {
        loader?.visibility = View.GONE
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}