// Generated code from Butter Knife. Do not modify!
package com.adityathakker.egyaan.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.adityathakker.egyaan.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class StudentInformationActivity_ViewBinding implements Unbinder {
  private StudentInformationActivity target;

  @UiThread
  public StudentInformationActivity_ViewBinding(StudentInformationActivity target, View source) {
    this.target = target;

    target.imageViewProfileImage = Utils.findRequiredViewAsType(source, R.id.studentProfileImage, "field 'imageViewProfileImage'", ImageView.class);
    target.textViewStudentEmail = Utils.findRequiredViewAsType(source, R.id.studentEmail, "field 'textViewStudentEmail'", TextView.class);
    target.textViewStudentPhone = Utils.findRequiredViewAsType(source, R.id.studentPhoneNumber, "field 'textViewStudentPhone'", TextView.class);
    target.textViewGender = Utils.findRequiredViewAsType(source, R.id.studentGender, "field 'textViewGender'", TextView.class);
    target.textViewParentName = Utils.findRequiredViewAsType(source, R.id.parentName, "field 'textViewParentName'", TextView.class);
    target.textViewParentEmail = Utils.findRequiredViewAsType(source, R.id.parentEmail, "field 'textViewParentEmail'", TextView.class);
    target.textViewParentPhone = Utils.findRequiredViewAsType(source, R.id.parentPhoneNumber, "field 'textViewParentPhone'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    StudentInformationActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imageViewProfileImage = null;
    target.textViewStudentEmail = null;
    target.textViewStudentPhone = null;
    target.textViewGender = null;
    target.textViewParentName = null;
    target.textViewParentEmail = null;
    target.textViewParentPhone = null;
  }
}
