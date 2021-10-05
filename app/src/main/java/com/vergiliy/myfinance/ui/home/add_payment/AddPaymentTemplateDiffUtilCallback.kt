package com.vergiliy.myfinance.ui.home.add_payment

import androidx.recyclerview.widget.DiffUtil
import com.vergiliy.myfinance.ui.models.PaymentTemplate

class AddPaymentTemplateDiffUtilCallback (private val oldList :List<PaymentTemplate>,
                                          private val newList: List<PaymentTemplate> ): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldProduct: PaymentTemplate = oldList[oldItemPosition]
        val newProduct: PaymentTemplate = newList[newItemPosition]
        return oldProduct.paymentParam === newProduct.paymentParam
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldProduct: PaymentTemplate = oldList[oldItemPosition]
        val newProduct: PaymentTemplate = newList[newItemPosition]
        return (oldProduct.comment.equals(newProduct.comment)
                && (oldProduct.realSum === newProduct.realSum) && (oldProduct.isSelected == newProduct.isSelected))
    }
}