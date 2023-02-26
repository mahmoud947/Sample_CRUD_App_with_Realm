package com.example.realmsample.ui.main.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.realmsample.databinding.ItemUsersBinding
import com.example.realmsample.data.local.entity.UserEntity
import com.example.realmsample.models.User

class UsersAdapters(private val interaction: Interaction? = null) :
    ListAdapter<User, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {

            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id==newItem.id
            }


            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem==newItem
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return UserViewHolder.from(parent, interaction = interaction)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserViewHolder -> {
                val item = getItem(position)
                holder.onBind(item)
            }
        }
    }


    class UserViewHolder constructor(
        private val binding: ItemUsersBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: User) {
            binding.user = item
            binding.root.setOnClickListener {
                interaction?.onItemSelected(this.adapterPosition, item)
            }
        }


        companion object {
            fun from(viewGroup: ViewGroup, interaction: Interaction?): UserViewHolder {
                val bind = ItemUsersBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                return UserViewHolder(bind, interaction = interaction)
            }
        }


    }

    interface Interaction {
        fun onItemSelected(position: Int, item: User)
    }
}
