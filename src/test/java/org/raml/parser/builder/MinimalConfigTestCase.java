package org.raml.parser.builder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.Raml;
import org.raml.model.ParamType;
import org.raml.model.Resource;
import org.raml.parser.visitor.BuilderNodeHandler;
import org.apache.commons.io.IOUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

public class MinimalConfigTestCase
{

    @Test
    public void basicConfig() throws Exception
    {
        String simpleTest = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("org/raml/root-elements.yaml"));
        BuilderNodeHandler<Raml> havenSpecBuilder = new BuilderNodeHandler<Raml>(Raml.class);
        Raml raml = havenSpecBuilder.build(simpleTest);
        assertThat(raml.getTitle(), is("Sample API"));
        assertThat(raml.getVersion(), is("v1"));
        assertThat(raml.getBaseUri(), is("https://{host}.sample.com/{path}"));

        assertThat(raml.getUriParameters().size(), is(2));
        assertThat(raml.getUriParameters().get("host").getName(), is("Host"));
        assertThat(raml.getUriParameters().get("host").getType(), is(ParamType.STRING));
        assertThat(raml.getUriParameters().get("path").getName(), is("Path"));
        assertThat(raml.getUriParameters().get("path").getType(), is(ParamType.STRING));

        assertThat(raml.getResources().size(), is(1));
        Resource mediaResource = raml.getResources().get("/media");
        assertThat(mediaResource.getName(), is("Media"));
        Action action = mediaResource.getAction(ActionType.GET);
        assertThat(action.getName(), is("retrieve"));
        assertThat(action.getBody().size(), is(1));
        assertThat(action.getBody().get("application/json"), CoreMatchers.notNullValue());

        assertThat(raml.getDocumentation().size(), is(2));
    }
}