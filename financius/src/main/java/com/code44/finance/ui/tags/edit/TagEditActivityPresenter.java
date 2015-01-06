package com.code44.finance.ui.tags.edit;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.text.TextUtils;
import android.widget.EditText;

import com.code44.finance.R;
import com.code44.finance.data.DataStore;
import com.code44.finance.data.db.Tables;
import com.code44.finance.data.model.Tag;
import com.code44.finance.data.providers.TagsProvider;
import com.code44.finance.ui.common.BaseActivity;
import com.code44.finance.ui.common.presenters.ModelEditActivityPresenter;
import com.code44.finance.utils.EventBus;
import com.code44.finance.utils.ThemeUtils;

class TagEditActivityPresenter extends ModelEditActivityPresenter<Tag> {
    private EditText titleEditText;
    private String title;

    public TagEditActivityPresenter(EventBus eventBus) {
        super(eventBus);
    }

    @Override public void onActivityCreated(BaseActivity activity, Bundle savedInstanceState) {
        super.onActivityCreated(activity, savedInstanceState);
        titleEditText = findView(activity, R.id.titleEditText);
    }

    @Override protected void onDataChanged(Tag storedModel) {

    }

    @Override protected boolean onSave() {
        boolean canSave = true;

        final String title = getTitle();
        if (TextUtils.isEmpty(title)) {
            canSave = false;
            titleEditText.setHintTextColor(ThemeUtils.getColor(titleEditText.getContext(), R.attr.textColorNegative));
        }

        if (canSave) {
            Tag tag = new Tag();


            DataStore.insert().model(model).into(this, TagsProvider.uriTags());
        }

        return canSave;
    }

    @Override protected CursorLoader getModelCursorLoader(Context context, String modelId) {
        return Tables.Tags.getQuery().asCursorLoader(context, TagsProvider.uriTag(modelId));
    }

    @Override protected Tag getModelFrom(Cursor cursor) {
        return Tag.from(cursor);
    }

    private String getId() {
        return getStoredModel() != null
    }

    private String getTitle() {
        if (title != null) {
            return title;
        }

        if (getStoredModel() != null) {
            return getStoredModel().getTitle();
        }

        return null;
    }
}
