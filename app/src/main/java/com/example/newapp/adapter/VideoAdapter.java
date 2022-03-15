package com.example.newapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dueeeke.videocontroller.component.PrepareView;
import com.example.newapp.R;
import com.example.newapp.entity.VideoEntity;
import com.example.newapp.listener.OnItemChildClickListener;
import com.example.newapp.listener.OnItemClickListener;
import com.example.newapp.view.CircleTransform;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mContext;
    private List<VideoEntity> mdatas;
    private OnItemChildClickListener mOnItemChildClickListener;

    private OnItemClickListener mOnItemClickListener;

    public VideoAdapter(Context context, List<VideoEntity> datas) {
        this.mContext = context;
        this.mdatas = datas;
    }

    public void setDatas(List<VideoEntity> datas) {
        this.mdatas = datas;
    }

    public VideoAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //把item 绑定这个viewholder
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_video_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //绑定 数据到viewholder中

        ViewHolder vh = (ViewHolder) holder;
        VideoEntity videoEntity = mdatas.get(holder.getAdapterPosition());
        vh.tv_title.setText(videoEntity.getVtitle());
        vh.tv_author.setText(videoEntity.getAuthor());
        // vh.tv_collect.setText(String.valueOf(videoEntity.().size()));
       // vh.tv_comment.setText(String.valueOf(videoEntity.getVideoSocialEntity().getCommentnum()));
      //  vh.tv_dz.setText(String.valueOf(videoEntity.getVideoSocialEntity().getLikenum()));

        //
        Picasso.with(mContext)
                .load(videoEntity.getHeadurl())
                .transform((Transformation) new CircleTransform())
                .into(vh.img_header);
       Picasso.with(mContext).
               load(videoEntity.getCoverurl())
               .into(vh.mThumb);
        //   vh.mPosition = position;

        vh.mPosition = holder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        if (mdatas != null && mdatas.size() > 0) {
            return mdatas.size();
        } else {
            return 0;
        }
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tv_title;
        public TextView tv_author;
        public TextView tv_comment;
        public TextView tv_collect;
        public TextView tv_dz;
        public ImageView img_header;
        //public ImageView img_cover;
        public ImageView mThumb;
        public PrepareView mPrepareView;
        public FrameLayout mPlayerContainer;
        public int mPosition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.title);
            tv_author = itemView.findViewById(R.id.author);
            tv_comment = itemView.findViewById(R.id.comment);
            tv_collect = itemView.findViewById(R.id.collect);
            tv_dz = itemView.findViewById(R.id.dz);
            img_header = itemView.findViewById(R.id.img_header);
            // img_cover = itemView.findViewById(R.id.img_cover);
            mPlayerContainer = itemView.findViewById(R.id.player_container);
            mPrepareView = itemView.findViewById(R.id.prepare_view);
            mThumb = mPrepareView.findViewById(R.id.thumb);
            if (mOnItemClickListener != null) {
                itemView.setOnClickListener(this);
            }
            if (mOnItemChildClickListener != null) {
                mPlayerContainer.setOnClickListener(this);
            }
            //通过tag将ViewHolder和itemView绑定
            itemView.setTag(this);
        }


        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.player_container) {
                if (mOnItemChildClickListener != null) {
                    mOnItemChildClickListener.onItemChildClick(mPosition);
                }
            } else {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(mPosition);
                }
            }

        }

    }

    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        mOnItemChildClickListener = onItemChildClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

}



