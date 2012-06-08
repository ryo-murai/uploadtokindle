package org.ry0mry.uploadtokindle.model;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class UploadUserTest extends AppEngineTestCase {

    private UploadUser model = new UploadUser();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}
