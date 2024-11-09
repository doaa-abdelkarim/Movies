package com.ragabz.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

/**
 * Base Databinding fragment
 */
abstract class BaseDBFragment<DB : ViewDataBinding, VM : ViewModel>(
    @LayoutRes val contentId: Int
) : Fragment() {

    protected abstract val viewModel: VM

    private var _binding: DB? = null
    protected val binding: DB
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(layoutInflater, contentId, container, false)
        onInitBinding()
        return binding.root
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun onInitBinding()
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = viewBindingInflater.invoke(layoutInflater, container, false)
        onInitBinding()
        return binding.root
    }

    abstract fun onInitBinding()

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}