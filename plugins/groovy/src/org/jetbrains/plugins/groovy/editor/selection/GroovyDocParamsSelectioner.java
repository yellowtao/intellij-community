/*
 * Copyright 2000-2008 JetBrains s.r.o.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.plugins.groovy.editor.selection;

import com.intellij.psi.PsiElement;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.editor.Editor;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.expressions.GrTypeCastExpression;
import org.jetbrains.plugins.groovy.lang.psi.api.types.GrTypeElement;
import org.jetbrains.plugins.groovy.lang.groovydoc.psi.api.GrDocMethodParams;

import java.util.List;

/**
 * @author ilyas
 */
public class GroovyDocParamsSelectioner extends GroovyBasicSelectioner {
  public boolean canSelect(PsiElement e) {
    return e instanceof GrDocMethodParams;
  }

  public List<TextRange> select(PsiElement element, CharSequence editorText, int cursorOffset, Editor editor) {
    List<TextRange> result = super.select(element, editorText, cursorOffset, editor);

    if (element instanceof GrDocMethodParams) {
      GrDocMethodParams params = ((GrDocMethodParams) element);
      TextRange range = params.getTextRange();
      if (range.contains(cursorOffset)) {
        PsiElement leftParen = params.getLeftParen();
        PsiElement rightParen = params.getRightParen();
        int leftOffset = leftParen.getTextOffset();
        if (rightParen != null) {
          if (leftOffset + 1 < rightParen.getTextOffset()) {
            int rightOffset = rightParen.getTextRange().getEndOffset();
            range = new TextRange(leftParen.getTextRange().getStartOffset() + 1, rightOffset - 1);
            result.add(range);
          }
        } else {
          range = new TextRange(leftParen.getTextRange().getStartOffset(), element.getTextRange().getEndOffset());
          result.add(range);
        }
      }
    }
    return result;
  }
}