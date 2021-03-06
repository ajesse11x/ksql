/*
 * Copyright 2018 Confluent Inc.
 *
 * Licensed under the Confluent Community License (the "License"); you may not use
 * this file except in compliance with the License.  You may obtain a copy of the
 * License at
 *
 * http://www.confluent.io/confluent-community-license
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */

package io.confluent.ksql.test.model.matchers;

import io.confluent.ksql.util.KsqlStatementException;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public final class KsqlExceptionMatcher {
  private KsqlExceptionMatcher() {
  }

  public static Matcher<?  super KsqlStatementException> statementText(
      final Matcher<String> statementMatcher
  ) {
    return new TypeSafeDiagnosingMatcher<KsqlStatementException>() {
      @Override
      protected boolean matchesSafely(
          final KsqlStatementException actual,
          final Description mismatchDescription) {
        if (!statementMatcher.matches(actual.getSqlStatement())) {
          statementMatcher.describeMismatch(actual.getSqlStatement(), mismatchDescription);
          return false;
        }
        return true;
      }

      @Override
      public void describeTo(final Description description) {
        description.appendText("statement text ").appendDescriptionOf(statementMatcher);
      }
    };
  }
}