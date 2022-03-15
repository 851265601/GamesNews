package com.example.newapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newapp.R;
import com.example.newapp.entity.NewsEntity;
import com.example.newapp.view.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<NewsEntity> datas;
    public NewsAdapter(Context context) {
         this.mContext = context;
    }

    public void setDatas(List<NewsEntity> datas) {
        this.datas = datas;
    }
    @Override
    public int getItemViewType(int position) {
        int type= datas.get(position).getType();
        return type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.news_item_one, parent, false);
            return new ViewHolderOne(view);
        } else if (viewType == 2) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.news_item_two, parent, false);
            return new ViewHolderTwo(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.news_item_three, parent, false);
            return new ViewHolderThree(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            int type=getItemViewType(position);
            NewsEntity newsEntity=datas.get(position);
            if(type==1){
                ViewHolderOne vh= (ViewHolderOne)holder;
                vh.title.setText(newsEntity.getNewsTitle());
                vh.author.setText(newsEntity.getAuthorName());
                vh.comment.setText(newsEntity.getCommentCount() + "评论 .");
                vh.time.setText(newsEntity.getReleaseDate());
                vh.newsEntity=newsEntity;
                Picasso.with(mContext)
                        .load(newsEntity.getHeaderUrl())
                        .transform(new CircleTransform())
                        .into(vh.header);

                Picasso.with(mContext)
                        .load(newsEntity.getThumbEntities().get(0).getThumbUrl())
                        .into(vh.thumb);
            }else if (type==2){
                ViewHolderTwo vh=  (ViewHolderTwo)holder;
                vh.title.setText(newsEntity.getNewsTitle());
                vh.author.setText(newsEntity.getAuthorName());
                vh.comment.setText(newsEntity.getCommentCount() + "评论 .");
                vh.time.setText(newsEntity.getReleaseDate());
                vh.newsEntity = newsEntity;
                Picasso.with(mContext)
                        .load(newsEntity.getHeaderUrl())
                        .transform(new CircleTransform())
                        .into(vh.header);

                Picasso.with(mContext)
                        .load(newsEntity.getThumbEntities().get(0).getThumbUrl())
                        .into(vh.pic1);
                Picasso.with(mContext)
                        .load(newsEntity.getThumbEntities().get(1).getThumbUrl())
                        .into(vh.pic2);
                Picasso.with(mContext)
                        .load(newsEntity.getThumbEntities().get(2).getThumbUrl())
                        .into(vh.pic3);
            }else {
                ViewHolderThree vh=(ViewHolderThree) holder;
                vh.title.setText(newsEntity.getNewsTitle());
                vh.author.setText(newsEntity.getAuthorName());
                vh.comment.setText(newsEntity.getCommentCount() + "评论 .");
                vh.time.setText(newsEntity.getReleaseDate());
                vh.newsEntity = newsEntity;
                Picasso.with(mContext)
                        .load(newsEntity.getHeaderUrl())
                        .transform(new CircleTransform())
                        .into(vh.header);

                Picasso.with(mContext)
                        .load(newsEntity.getThumbEntities().get(0).getThumbUrl())
                        .into(vh.thumb);
            }
    }

    @Override
    public int getItemCount() {
       if(datas!=null&&datas.size()>0){
          return datas.size();
       }else {
           return 0;
       }
    }
    public class ViewHolderOne extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView author;
        private TextView comment;
        private TextView time;
        private ImageView header;
        private ImageView thumb;
        private NewsEntity newsEntity;

        public ViewHolderOne(@NonNull View view) {
            super(view);
            title = view.findViewById(R.id.title);
            author = view.findViewById(R.id.author);
            comment = view.findViewById(R.id.comment);
            time = view.findViewById(R.id.time);
            header = view.findViewById(R.id.header);
            thumb = view.findViewById(R.id.thumb);

        }
    }

    public class ViewHolderTwo extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView author;
        private TextView comment;
        private TextView time;
        private ImageView header;
        private ImageView pic1, pic2, pic3;
        private NewsEntity newsEntity;

        public ViewHolderTwo(@NonNull View view) {
            super(view);
            title = view.findViewById(R.id.title);
            author = view.findViewById(R.id.author);
            comment = view.findViewById(R.id.comment);
            time = view.findViewById(R.id.time);
            header = view.findViewById(R.id.header);
            pic1 = view.findViewById(R.id.pic1);
            pic2 = view.findViewById(R.id.pic2);
            pic3 = view.findViewById(R.id.pic3);

        }
    }

    public class ViewHolderThree extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView author;
        private TextView comment;
        private TextView time;
        private ImageView header;
        private ImageView thumb;
        private NewsEntity newsEntity;

        public ViewHolderThree(@NonNull View view) {
            super(view);
            title = view.findViewById(R.id.title);
            author = view.findViewById(R.id.author);
            comment = view.findViewById(R.id.comment);
            time = view.findViewById(R.id.time);
            header = view.findViewById(R.id.header);
            thumb = view.findViewById(R.id.thumb);

        }
    }

}
