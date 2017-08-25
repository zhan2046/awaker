package com.future.awaker.news.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.future.awaker.R;
import com.future.awaker.base.EmptyHolder;
import com.future.awaker.base.IDiffCallBack;
import com.future.awaker.base.listener.OnItemClickListener;
import com.future.awaker.data.Comment;
import com.future.awaker.data.Header;
import com.future.awaker.data.NewEle;
import com.future.awaker.databinding.ItemNewDetailCommentBinding;
import com.future.awaker.databinding.ItemNewDetailCommentMoreBinding;
import com.future.awaker.databinding.ItemNewDetailCommentTitleBinding;
import com.future.awaker.databinding.ItemNewDetailHeaderBinding;
import com.future.awaker.databinding.ItemNewDetailImgBinding;
import com.future.awaker.databinding.ItemNewDetailTextBinding;
import com.future.awaker.databinding.ItemNewDetailVideoBinding;
import com.future.awaker.news.adapter.holder.NewDetailCommentHolder;
import com.future.awaker.news.adapter.holder.NewDetailCommentMoreHolder;
import com.future.awaker.news.adapter.holder.NewDetailHeaderHolder;
import com.future.awaker.news.adapter.holder.NewDetailImgHolder;
import com.future.awaker.news.adapter.holder.NewDetailTextHolder;
import com.future.awaker.news.adapter.holder.NewDetailVideoHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Copyright ©2017 by ruzhan
 */

public class NewDetailAdapter extends RecyclerView.Adapter {

    private static final String COMMENT_TITLE = "commentTitle";
    private static final String COMMENT_MORE = "commentMore";

    private static final int TYPE_HEADER = 1000;
    private static final int TYPE_TEXT = 1001;
    private static final int TYPE_IMG = 1002;
    private static final int TYPE_VIDEO = 1003;
    private static final int TYPE_COMMENT_TITLE = 1004;
    private static final int TYPE_COMMENT_ITEM = 1005;
    private static final int TYPE_COMMENT_MORE = 1006;

    private List<Object> dataList = new ArrayList<>();
    private OnItemClickListener<NewEle> listener;
    private Header header;
    private List<NewEle> newEleList;
    private List<Comment> commentList;

    private List<NewDetailVideoHolder> videoHolders = new ArrayList<>();
    private NewDetailDiffCallBack diffCallBack = new NewDetailDiffCallBack();

    public NewDetailAdapter(Header header, OnItemClickListener<NewEle> listener) {
        this.header = header;
        this.listener = listener;
        dataList.add(header);
    }

    public void setData(List<NewEle> list, Header header) {
        if (list == null) {
            return;
        }
        List<Object> newDataList = new ArrayList<>();
        newEleList = list;
        this.header = header;

        newDataList.add(this.header);
        newDataList.addAll(newEleList);
        if (commentList != null && !commentList.isEmpty()) {
            newDataList.add(COMMENT_TITLE);
            newDataList.addAll(commentList);
            newDataList.add(COMMENT_MORE);
        }

        diffCallBack.setData(dataList, newDataList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallBack, false);
        diffResult.dispatchUpdatesTo(this);
        dataList = newDataList;
    }

    public void setCommentList(List<Comment> list) {
        if (list == null) {
            return;
        }
        List<Object> newDataList = new ArrayList<>();
        commentList = list;

        newDataList.add(header);
        if (newEleList != null && !newEleList.isEmpty()) {
            newDataList.addAll(newEleList);

            newDataList.add(COMMENT_TITLE);
            newDataList.addAll(commentList);
            newDataList.add(COMMENT_MORE);
        }
        diffCallBack.setData(dataList, newDataList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallBack, false);
        diffResult.dispatchUpdatesTo(this);
        dataList = newDataList;
    }

    @Override
    public int getItemViewType(int position) {
        Object object = dataList.get(position);
        if (object instanceof NewEle) {
            NewEle newEle = (NewEle) object;
            if (NewEle.TYPE_TEXT == newEle.type) {
                return TYPE_TEXT;
            } else if (NewEle.TYPE_IMG == newEle.type) {
                return TYPE_IMG;
            } else if (NewEle.TYPE_VIDEO == newEle.type) {
                return TYPE_VIDEO;
            }
        } else if (object instanceof String) {
            String str = (String) object;
            if (COMMENT_TITLE.equals(str)) {
                return TYPE_COMMENT_TITLE;

            } else if (COMMENT_MORE.equals(str)) {
                return TYPE_COMMENT_MORE;
            }
        } else if (object instanceof Comment) {
            return TYPE_COMMENT_ITEM;
        } else if (object instanceof Header) {
            return TYPE_HEADER;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TEXT) {
            ItemNewDetailTextBinding binding = DataBindingUtil
                    .inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.item_new_detail_text, parent, false);
            return new NewDetailTextHolder(binding);

        } else if (viewType == TYPE_IMG) {
            ItemNewDetailImgBinding binding = DataBindingUtil
                    .inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.item_new_detail_img, parent, false);
            NewDetailImgHolder holder = new NewDetailImgHolder(binding);
            binding.setListener(listener);
            binding.setHolder(holder);
            return holder;

        } else if (viewType == TYPE_VIDEO) {
            ItemNewDetailVideoBinding binding = DataBindingUtil
                    .inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.item_new_detail_video, parent, false);
            NewDetailVideoHolder holder = new NewDetailVideoHolder(binding, listener);
            binding.setListener(listener);
            binding.setHolder(holder);

            videoHolders.add(holder);
            return holder;

        } else if (viewType == TYPE_COMMENT_TITLE) {
            ItemNewDetailCommentTitleBinding binding = DataBindingUtil
                    .inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.item_new_detail_comment_title, parent, false);
            return new EmptyHolder(binding);

        } else if (viewType == TYPE_COMMENT_ITEM) {
            ItemNewDetailCommentBinding binding = DataBindingUtil
                    .inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.item_new_detail_comment, parent, false);
            return new NewDetailCommentHolder(binding);

        } else if (viewType == TYPE_COMMENT_MORE) {
            ItemNewDetailCommentMoreBinding binding = DataBindingUtil
                    .inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.item_new_detail_comment_more, parent, false);

            return new NewDetailCommentMoreHolder(binding, listener);

        } else if (viewType == TYPE_HEADER) {
            ItemNewDetailHeaderBinding binding = DataBindingUtil
                    .inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.item_new_detail_header, parent, false);
            return new NewDetailHeaderHolder(binding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == TYPE_TEXT) {
            ((NewDetailTextHolder) holder).bind((NewEle) dataList.get(position));

        } else if (viewType == TYPE_IMG) {
            ((NewDetailImgHolder) holder).bind((NewEle) dataList.get(position));

        } else if (viewType == TYPE_VIDEO) {
            ((NewDetailVideoHolder) holder).bind((NewEle) dataList.get(position));

        } else if (viewType == TYPE_COMMENT_ITEM) {
            ((NewDetailCommentHolder) holder).bind((Comment) dataList.get(position));

        } else if (viewType == TYPE_HEADER) {
            ((NewDetailHeaderHolder) holder).bind((Header) dataList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public void onResume() {
        for (NewDetailVideoHolder videoHolder : videoHolders) {
            videoHolder.mAgentWeb.getWebLifeCycle().onResume();
        }
    }

    public void onPause() {
        for (NewDetailVideoHolder videoHolder : videoHolders) {
            videoHolder.mAgentWeb.getWebLifeCycle().onPause();
        }
    }

    public void onDestroy() {
        for (NewDetailVideoHolder videoHolder : videoHolders) {
            videoHolder.mAgentWeb.getWebLifeCycle().onDestroy();
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        if (holder instanceof NewDetailVideoHolder) {
            NewDetailVideoHolder videoHolder = (NewDetailVideoHolder) holder;
            videoHolder.mAgentWeb.getWebLifeCycle().onPause();
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        if (holder instanceof NewDetailVideoHolder) {
            NewDetailVideoHolder videoHolder = (NewDetailVideoHolder) holder;
            videoHolder.mAgentWeb.getWebLifeCycle().onResume();
        }
    }

    private static class NewDetailDiffCallBack extends IDiffCallBack<Object> {

        @Override
        public boolean isItemsTheSame(int oldItemPosition, int newItemPosition) {
            Object oldObj = oldData.get(oldItemPosition);
            Object newsObj = newData.get(newItemPosition);

            if (oldObj instanceof NewEle && newsObj instanceof NewEle) {
                NewEle oldNewEle = (NewEle) oldObj;
                NewEle newNewEle = (NewEle) newsObj;
                if (oldNewEle.type == newNewEle.type) {
                    if (oldNewEle.type == TYPE_TEXT) {
                        return Objects.equals(oldNewEle.text, newNewEle.text);

                    } else if (oldNewEle.type == TYPE_IMG) {
                        return Objects.equals(oldNewEle.imgUrl, newNewEle.imgUrl);

                    } else if (oldNewEle.type == TYPE_VIDEO) {
                        return Objects.equals(oldNewEle.videoUrl, newNewEle.videoUrl);
                    }
                }

            } else if (oldObj instanceof Header && newsObj instanceof Header) {
                Header oldHeader = (Header) oldObj;
                Header newHeader = (Header) newsObj;
                return Objects.equals(oldHeader.url, newHeader.url);
            }
            return Objects.equals(oldObj, newsObj);
        }

        @Override
        public boolean isContentsTheSame(int oldItemPosition, int newItemPosition) {
            Object oldObj = oldData.get(oldItemPosition);
            Object newsObj = newData.get(newItemPosition);

            if (oldObj instanceof NewEle && newsObj instanceof NewEle) {
                NewEle oldNewEle = (NewEle) oldObj;
                NewEle newNewEle = (NewEle) newsObj;
                if (oldNewEle.type == newNewEle.type) {
                    if (oldNewEle.type == TYPE_TEXT) {
                        return Objects.equals(oldNewEle.text, newNewEle.text);

                    } else if (oldNewEle.type == TYPE_IMG) {
                        return Objects.equals(oldNewEle.imgUrl, newNewEle.imgUrl);

                    } else if (oldNewEle.type == TYPE_VIDEO) {
                        return Objects.equals(oldNewEle.videoUrl, newNewEle.videoUrl);
                    }
                }

            } else if (oldObj instanceof Header && newsObj instanceof Header) {
                Header oldHeader = (Header) oldObj;
                Header newHeader = (Header) newsObj;
                return Objects.equals(oldHeader.url, newHeader.url);
            }
            return Objects.equals(oldObj, newsObj);
        }
    }
}
