/*
 * Copyright (c) MuleSoft, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */

package org.raml.parser.rules;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.raml.parser.rule.SimpleRule;
import org.raml.parser.rule.ValidationResult;
import org.raml.parser.visitor.RamlValidationService;

public class TitleRuleTestCase
{

    @Test
    public void testTitleNotEmpty()
    {
        String raml = "#%RAML 0.8\n" + "---\n" + "title:";
        List<ValidationResult> errors = RamlValidationService.createDefault().validate(raml);
        Assert.assertFalse("Errors must not be empty", errors.isEmpty());
        Assert.assertThat(errors.get(0).getMessage(),
                          CoreMatchers.is(SimpleRule.getRuleEmptyMessage("title")));
    }

    @Test
    public void testTitlePresent()
    {
        String raml = "#%RAML 0.8\n" + "---\n" + "version: v28.0\n";
        List<ValidationResult> errors = RamlValidationService.createDefault().validate(raml);
        Assert.assertFalse("Errors must not be empty", errors.isEmpty());
        Assert.assertThat(errors.get(0).getMessage(),
                          CoreMatchers.is(SimpleRule.getMissingRuleMessage("title")));
    }

    @Test
    public void testTitleNotMoreThanOnce()
    {
        String raml = "#%RAML 0.8\n" + "---\n" + "title: bla \n" + "title: bla";
        List<ValidationResult> errors = RamlValidationService.createDefault().validate(raml);
        Assert.assertFalse("Errors must not be empty", errors.isEmpty());
        Assert.assertThat(errors.get(0).getMessage(),
                          CoreMatchers.is(SimpleRule.getDuplicateRuleMessage("title")));
    }

}
