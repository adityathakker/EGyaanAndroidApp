// Generated code from Butter Knife. Do not modify!
package com.adityathakker.egyaan.ui.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.adityathakker.egyaan.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view2131230749;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    target.stripsBackground = Utils.findRequiredViewAsType(source, R.id.activity_login_imageview_strips, "field 'stripsBackground'", ImageView.class);
    target.egyaanLogo = Utils.findRequiredViewAsType(source, R.id.activity_login_imageview_egyaan_logo, "field 'egyaanLogo'", ImageView.class);
    target.supportLogin = Utils.findRequiredViewAsType(source, R.id.activity_login_textview_support_login, "field 'supportLogin'", TextView.class);
    target.supportWelcome = Utils.findRequiredViewAsType(source, R.id.activity_login_textview_support_welcome, "field 'supportWelcome'", TextView.class);
    target.emailEditText = Utils.findRequiredViewAsType(source, R.id.activity_login_edittext_email_address, "field 'emailEditText'", EditText.class);
    target.passwordEditText = Utils.findRequiredViewAsType(source, R.id.activity_login_edittext_password, "field 'passwordEditText'", EditText.class);
    view = Utils.findRequiredView(source, R.id.activity_login_button_login, "field 'loginButton' and method 'validateAndLogin'");
    target.loginButton = Utils.castView(view, R.id.activity_login_button_login, "field 'loginButton'", Button.class);
    view2131230749 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.validateAndLogin(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.stripsBackground = null;
    target.egyaanLogo = null;
    target.supportLogin = null;
    target.supportWelcome = null;
    target.emailEditText = null;
    target.passwordEditText = null;
    target.loginButton = null;

    view2131230749.setOnClickListener(null);
    view2131230749 = null;
  }
}
