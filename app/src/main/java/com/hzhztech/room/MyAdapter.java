package com.hzhztech.room;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private final WordViewModel wordViewModel;
    List<Word> allWords = new ArrayList<>();
    private boolean useCardView;

    public MyAdapter(boolean useCardView, WordViewModel wordViewModel) {
        this.useCardView = useCardView;
        this.wordViewModel = wordViewModel;
    }

    public void setAllWords(List<Word> allWords) {
        this.allWords = allWords;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if(useCardView) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_card,parent,false);
        }else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_normal2,parent,false);
        }
        final MyViewHolder holder = new MyViewHolder(itemView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://m.youdao.com/dict?le=eng&q=" + holder.textViewEnglish.getText());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                holder.itemView.getContext().startActivity(intent);
            }
        });
        holder.aSwitchChineseInvisible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Word word = (Word) holder.itemView.getTag(R.id.word_for_view_holder);
                if (b) {
                    //修改视图
                    holder.textViewChinese.setVisibility(View.GONE);
                    //修改数据
                    word.setChineseINvisible(true);
                    wordViewModel.updateWords(word);
                }else {
                    holder.textViewChinese.setVisibility(View.VISIBLE);
                    word.setChineseINvisible(false);
                    wordViewModel.updateWords(word);
                }
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Word word = allWords.get(position);
        holder.itemView.setTag(R.id.word_for_view_holder,word);
        holder.textViewId.setText(position + "");
        holder.textViewEnglish.setText(word.getWord());
        holder.textViewChinese.setText(word.getChineseMeaning());
        //不希望驱动 下面的setOnCheckedChangeListener, 那么先设置为null, 显示数据,然后设置监听器
//        holder.aSwitchChineseInvisible.setOnCheckedChangeListener(null);
        //底层数据决定是否显示
        if(word.isChineseINvisible()) {
            holder.textViewChinese.setVisibility(View.GONE);
            holder.aSwitchChineseInvisible.setChecked(true);
        }else {
            holder.textViewChinese.setVisibility(View.VISIBLE);
            holder.aSwitchChineseInvisible.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return allWords.size();
    }

    //内部类加上static 防止内存泄露
    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewId,textViewEnglish,textViewChinese;
        Switch aSwitchChineseInvisible;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewId = itemView.findViewById(R.id.textViewId);
            textViewEnglish = itemView.findViewById(R.id.textViewEnglish);
            textViewChinese = itemView.findViewById(R.id.textViewChinese);
            aSwitchChineseInvisible = itemView.findViewById(R.id.switchChineseinvisible);
        }
    }
}
