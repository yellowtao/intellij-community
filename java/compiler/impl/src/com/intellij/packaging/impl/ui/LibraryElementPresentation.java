package com.intellij.packaging.impl.ui;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.roots.libraries.LibraryTable;
import com.intellij.openapi.roots.libraries.LibraryTablePresentation;
import com.intellij.openapi.roots.impl.ModuleLibraryTable;
import com.intellij.openapi.roots.impl.libraries.LibraryImpl;
import com.intellij.openapi.module.Module;
import com.intellij.packaging.ui.ArtifactEditorContext;
import com.intellij.packaging.ui.PackagingElementPresentation;
import com.intellij.packaging.ui.PackagingElementWeights;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.util.Icons;
import org.jetbrains.annotations.NotNull;

/**
 * @author nik
 */
public class LibraryElementPresentation extends PackagingElementPresentation {
  private final Library myLibrary;
  private final String myName;
  private final ArtifactEditorContext myContext;

  public LibraryElementPresentation(String level, String name, Library library, ArtifactEditorContext context) {
    myLibrary = library;
    myName = name;
    myContext = context;
  }

  public String getPresentableName() {
    return myName;
  }

  @Override
  public boolean canNavigateToSource() {
    return myLibrary != null;
  }

  @Override
  public Object getSourceObject() {
    return myLibrary;
  }

  @Override
  public void navigateToSource() {
    myContext.selectLibrary(myLibrary);
  }

  public void render(@NotNull PresentationData presentationData, SimpleTextAttributes mainAttributes, SimpleTextAttributes commentAttributes) {
    if (myLibrary != null) {
      presentationData.setIcons(Icons.LIBRARY_ICON);
      presentationData.addText(myName, mainAttributes);
      presentationData.addText(getLibraryTableComment(myLibrary), commentAttributes);
    }
    else {
      presentationData.addText(myName, SimpleTextAttributes.ERROR_ATTRIBUTES);
    }
  }

  @Override
  public int getWeight() {
    return PackagingElementWeights.LIBRARY;
  }

  public static String getLibraryTableDisplayName(final Library library) {
    LibraryTable table = library.getTable();
    LibraryTablePresentation presentation = table != null ? table.getPresentation() : ModuleLibraryTable.MODULE_LIBRARY_TABLE_PRESENTATION;
    return presentation.getDisplayName(false);
  }

  public static String getLibraryTableComment(final Library library) {
    LibraryTable libraryTable = library.getTable();
    String displayName;
    if (libraryTable != null) {
      displayName = libraryTable.getPresentation().getDisplayName(false);
    }
    else {
      Module module = ((LibraryImpl)library).getModule();
      String tableName = getLibraryTableDisplayName(library);
      displayName = module != null ? "'" + module.getName() + "' " + tableName : tableName;
    }
    return " (" + displayName + ")";
  }
}
