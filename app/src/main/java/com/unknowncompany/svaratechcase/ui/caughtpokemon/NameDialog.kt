package com.unknowncompany.svaratechcase.ui.caughtpokemon

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.unknowncompany.svaratechcase.R
import com.unknowncompany.svaratechcase.databinding.DialogNameBinding

class NameDialog(
    private val confirmCallback: (name: String) -> Unit,
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { it ->
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val binding = DialogNameBinding.inflate(inflater)

            binding.ibClose.setOnClickListener {
                dismiss()
            }
            binding.btnCancel.setOnClickListener {
                dismiss()
            }
            binding.btnConfirm.setOnClickListener {
                val name = binding.edName.text.toString().trim()
                dismiss()
                confirmCallback.invoke(name)
            }

            builder.setView(binding.root)
            builder.create()

        } ?: throw IllegalStateException(getString(R.string.null_activity))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        val width = resources.getDimensionPixelSize(R.dimen.dialog_width)
        val height = resources.getDimensionPixelSize(R.dimen.dialog_height)
        dialog?.window?.setLayout(width, height)
        super.onStart()
    }

    override fun onResume() {
        val width = resources.getDimensionPixelSize(R.dimen.dialog_width)
        val height = resources.getDimensionPixelSize(R.dimen.dialog_height)
        dialog?.window?.setLayout(width, height)
        super.onResume()
    }

}