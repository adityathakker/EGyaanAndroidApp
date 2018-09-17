// Generated code from Butter Knife. Do not modify!
package com.adityathakker.egyaan.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.adityathakker.egyaan.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HomeActivity_ViewBinding implements Unbinder {
  private HomeActivity target;

  private View view2131230761;

  private View view2131230757;

  private View view2131230760;

  private View view2131230756;

  @UiThread
  public HomeActivity_ViewBinding(final HomeActivity target, View source) {
    this.target = target;

    View view;
    target.recyclerViewTimetable = Utils.findRequiredViewAsType(source, R.id.recycler_view_home_timetable, "field 'recyclerViewTimetable'", RecyclerView.class);
    target.textViewTimetableDayHome = Utils.findRequiredViewAsType(source, R.id.textView_timetable_day_home, "field 'textViewTimetableDayHome'", TextView.class);
    target.textViewTestTitleHome = Utils.findRequiredViewAsType(source, R.id.testTitleHome, "field 'textViewTestTitleHome'", TextView.class);
    target.textViewTestDateHome = Utils.findRequiredViewAsType(source, R.id.testDateHome, "field 'textViewTestDateHome'", TextView.class);
    target.textViewTestMarksHome = Utils.findRequiredViewAsType(source, R.id.testMarksHome, "field 'textViewTestMarksHome'", TextView.class);
    target.textViewTestTotalMarksHome = Utils.findRequiredViewAsType(source, R.id.testTotalMarksHome, "field 'textViewTestTotalMarksHome'", TextView.class);
    target.textViewTestAttendanceHome = Utils.findRequiredViewAsType(source, R.id.testAttendanceHome, "field 'textViewTestAttendanceHome'", TextView.class);
    target.textViewNoticeTitle = Utils.findRequiredViewAsType(source, R.id.textViewNoticeTitleMain, "field 'textViewNoticeTitle'", TextView.class);
    target.textViewNoticeDate = Utils.findRequiredViewAsType(source, R.id.textViewNoticeDateMain, "field 'textViewNoticeDate'", TextView.class);
    target.textViewNoticeTime = Utils.findRequiredViewAsType(source, R.id.textViewNoticeTimeMain, "field 'textViewNoticeTime'", TextView.class);
    target.textViewNoticeNoData = Utils.findRequiredViewAsType(source, R.id.textViewNoticeNoData, "field 'textViewNoticeNoData'", TextView.class);
    target.linearLayoutHideNoticeboard = Utils.findRequiredViewAsType(source, R.id.linearLayoutHomeNoticeboardDT, "field 'linearLayoutHideNoticeboard'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.activity_timeTable, "method 'linearLayoutTimetable'");
    view2131230761 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.linearLayoutTimetable(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.activity_noticeboard, "method 'linearLayoutNoticeboard'");
    view2131230757 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.linearLayoutNoticeboard(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.activity_tests, "method 'linearLayoutTests'");
    view2131230760 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.linearLayoutTests(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.activity_notes, "method 'linearLayoutNotes'");
    view2131230756 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.linearLayoutNotes(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    HomeActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerViewTimetable = null;
    target.textViewTimetableDayHome = null;
    target.textViewTestTitleHome = null;
    target.textViewTestDateHome = null;
    target.textViewTestMarksHome = null;
    target.textViewTestTotalMarksHome = null;
    target.textViewTestAttendanceHome = null;
    target.textViewNoticeTitle = null;
    target.textViewNoticeDate = null;
    target.textViewNoticeTime = null;
    target.textViewNoticeNoData = null;
    target.linearLayoutHideNoticeboard = null;

    view2131230761.setOnClickListener(null);
    view2131230761 = null;
    view2131230757.setOnClickListener(null);
    view2131230757 = null;
    view2131230760.setOnClickListener(null);
    view2131230760 = null;
    view2131230756.setOnClickListener(null);
    view2131230756 = null;
  }
}
