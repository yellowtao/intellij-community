// Copyright 2000-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.ide.plugins;

import com.intellij.ide.dnd.FileCopyPasteUtil;
import com.intellij.openapi.editor.CustomFileDropHandler;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtilRt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.datatransfer.Transferable;
import java.io.File;
import java.nio.file.Path;
import java.util.List;

public final class PluginDropHandler extends CustomFileDropHandler {
  @Override
  public boolean canHandle(@NotNull Transferable t, @Nullable Editor editor) {
    Path file = getFile(t);
    if (file == null) {
      return false;
    }
    String path = file.toString();
    return FileUtilRt.extensionEquals(path, "jar") ||
           FileUtilRt.extensionEquals(path, "zip");
  }

  @Override
  public boolean handleDrop(@NotNull Transferable t, @Nullable Editor editor, Project project) {
    Path file = getFile(t);
    if (file == null) {
      return false;
    }
    return PluginInstaller.installFromDisk(new InstalledPluginsTableModel(), file, PluginInstallCallbackDataKt::installPluginFromCallbackData, null);
  }

  private static @Nullable Path getFile(@NotNull Transferable t) {
    List<File> list = FileCopyPasteUtil.getFileList(t);
    return list == null || list.size() != 1 ? null : list.get(0).toPath();
  }
}
