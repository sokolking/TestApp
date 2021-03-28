package com.test.testapp.features.search.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.domain.api.comments.Comment
import com.test.testapp.R
import com.test.testapp.utils.recycler_view.AdapterItem
import com.test.testapp.utils.recycler_view.MultiTypeRecyclerAdapter
import kotlinx.android.synthetic.main.item_comment.view.*

class CommentAdapter : MultiTypeRecyclerAdapter() {

    override fun createViewHolder(parent: ViewGroup, item: AdapterItem): RecyclerView.ViewHolder {
        return when (item) {
            is CommentItem -> {
                SearchRouteViewHolder(inflate(parent, R.layout.item_comment))
            }
            else -> {
                throw IllegalStateException()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchRouteViewHolder -> {
                holder.bind(getItem<CommentItem>(position).comment)
            }
        }
    }

    fun showComments(comments: List<Comment>) {
        val items = mutableListOf<AdapterItem>()
        comments.forEach { route ->
            items.add(CommentItem(route))
        }
        update(items)
    }

    class CommentItem(val comment: Comment) : AdapterItem(ADAPTER_ITEM_COMMENT) {
        override fun isItemSame(other: AdapterItem): Boolean {
            return comment.id == (other as? CommentItem)?.comment?.id
        }

        override fun isContentSame(other: AdapterItem): Boolean {
            return comment.name == (other as? CommentItem)?.comment?.name
        }
    }

    inner class SearchRouteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val context = view.context
        private var currentComment: Comment? = null
        private val commentPostId = view.tv_post_id
        private val commentId = view.tv_id
        private val commentName = view.tv_name
        private val commentEmail = view.tv_email
        private val commentBody = view.tv_body

        fun bind(comment: Comment) {
            currentComment = comment
            commentPostId.text =
                context.getString(R.string.comment_item_post_id, comment.postId ?: 0)
            commentId.text = context.getString(R.string.comment_item_id, comment.id ?: 0)
            commentName.text = context.getString(R.string.comment_item_name, comment.name ?: "")
            commentEmail.text = context.getString(R.string.comment_item_email, comment.email ?: "")
            commentBody.text = context.getString(R.string.comment_item_body, comment.body ?: "")
        }
    }

    companion object {
        const val ADAPTER_ITEM_COMMENT = 1
    }
}