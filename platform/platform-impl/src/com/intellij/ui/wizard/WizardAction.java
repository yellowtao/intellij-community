// Copyright 2000-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.ui.wizard;

import com.intellij.openapi.util.NlsActions;

import javax.swing.*;
import java.awt.event.ActionEvent;

public abstract class WizardAction extends AbstractAction {

  protected WizardModel myModel;

  public WizardAction(@NlsActions.ActionText String name, WizardModel model) {
    super(name);
    myModel = model;
  }

  protected void setMnemonic(char value) {
    putValue(Action.MNEMONIC_KEY, Integer.valueOf(value));
  }

  public final void setName(@NlsActions.ActionText String name) {
    putValue(Action.NAME, name);
  }

  public static class Next extends WizardAction {

    public Next(WizardModel model) {
      super("Next >", model);
      setMnemonic('N');
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      myModel.next();
    }
  }

  public static class Previous extends WizardAction {

    public Previous(WizardModel model) {
      super("< Previous", model);
      setMnemonic('P');
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      myModel.previous();
    }
  }

  public static class Finish extends WizardAction {

    public Finish(WizardModel model) {
      super("Finish", model);
      setMnemonic('F');
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      myModel.finish();
    }
  }

  public static class Cancel extends WizardAction {

    public Cancel(WizardModel model) {
      super("Cancel", model);
      setMnemonic('C');
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      myModel.cancel();
    }
  }


}