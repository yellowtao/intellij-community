// Copyright 2000-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.codeInspection.ui.actions;

import com.intellij.codeInspection.ui.InspectionResultsView;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author Dmitry Batkovich
 */
public abstract class InspectionViewActionBase extends AnAction {
  public InspectionViewActionBase(@Nullable String text, @Nullable String description, @Nullable Icon icon) {
    super(text, description, icon);
  }

  public InspectionViewActionBase(String name) {
    super(name);
  }

  @Override
  public final void update(AnActionEvent e) {
    final InspectionResultsView view = getView(e);
    final boolean enabled = view != null && isEnabled(view, e);
    final Presentation presentation = e.getPresentation();
    presentation.setEnabled(enabled);
  }

  protected boolean isEnabled(@NotNull InspectionResultsView view, AnActionEvent e) {
    return true;
  }

  @Nullable
  public static InspectionResultsView getView(@Nullable AnActionEvent event) {
    if (event == null) {
      return null;
    }
    InspectionResultsView view = InspectionResultsView.DATA_KEY.getData(event.getDataContext());
    if (view == null) {
      Project project = event.getProject();
      if (project == null) return null;
      ToolWindowManager twManager = ToolWindowManager.getInstance(project);
      ToolWindow window = twManager.getToolWindow(ToolWindowId.INSPECTION);
      if (window == null) return null;
      Content selectedContent = window.getContentManager().getSelectedContent();
      if (selectedContent == null) return null;
      DataContext twContext = DataManager.getInstance().getDataContext(selectedContent.getComponent());
      view = InspectionResultsView.DATA_KEY.getData(twContext);
      if (view == null) return null;
    }
    return view.isDisposed() ? null : view;
  }
}
