// Generated code from Butter Knife. Do not modify!
package com.adityathakker.egyaan.ui.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.adityathakker.egyaan.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TimetableActivity_ViewBinding implements Unbinder {
  private TimetableActivity target;

  @UiThread
  public TimetableActivity_ViewBinding(TimetableActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public TimetableActivity_ViewBinding(TimetableActivity target, View source) {
    this.target = target;

    target.toolbarTimetable = Utils.findRequiredViewAsType(source, R.id.toolbar_timetable_activity, "field 'toolbarTimetable'", Toolbar.class);
    target.viewPagerTimetable = Utils.findRequiredViewAsType(source, R.id.activity_view_pager, "field 'viewPagerTimetable'", ViewPager.class);
    target.tabLayoutTimetable = Utils.findRequiredViewAsType(source, R.id.activity_tab_layout, "field 'tabLayoutTimetable'", TabLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TimetableActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbarTimetable = null;
    target.viewPagerTimetable = null;
    target.tabLayoutTimetable = null;
  }
}
