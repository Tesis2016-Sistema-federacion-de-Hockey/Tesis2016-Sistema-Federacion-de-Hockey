/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.integtests.tests.modules.simple;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.inject.Inject;

import com.google.common.base.Throwables;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.FixtureScripts;

import domainapp.dom.jugador.Jugador;
import domainapp.dom.jugador.Jugadores;
import domainapp.fixture.dom.simple.SimpleObjectsTearDown;
import domainapp.fixture.scenarios.RecreateSimpleObjects;
import domainapp.integtests.tests.DomainAppIntegTest;
import static org.assertj.core.api.Assertions.assertThat;

public class SimpleObjectsIntegTest extends DomainAppIntegTest {

    @Inject
    FixtureScripts fixtureScripts;
    @Inject
    Jugadores simpleObjects;

    public static class ListAll extends SimpleObjectsIntegTest {

        @Test
        public void happyCase() throws Exception {

            // given
            RecreateSimpleObjects fs = new RecreateSimpleObjects();
            fixtureScripts.runFixtureScript(fs, null);
            nextTransaction();

            // when
            final List<Jugador> all = wrap(simpleObjects).listarTodosLosJugadores();

            // then
            assertThat(all).hasSize(fs.getSimpleObjects().size());

            Jugador simpleObject = wrap(all.get(0));
            assertThat(simpleObject.getNombre()).isEqualTo(fs.getSimpleObjects().get(0).getNombre());
        }

        @Test
        public void whenNone() throws Exception {

            // given
            FixtureScript fs = new SimpleObjectsTearDown();
            fixtureScripts.runFixtureScript(fs, null);
            nextTransaction();

            // when
            final List<Jugador> all = wrap(simpleObjects).listarTodosLosJugadores();

            // then
            assertThat(all).hasSize(0);
        }
    }

    public static class Create extends SimpleObjectsIntegTest {

        @Test
        public void happyCase() throws Exception {

            // given
            FixtureScript fs = new SimpleObjectsTearDown();
            fixtureScripts.runFixtureScript(fs, null);
            nextTransaction();

            // when
            wrap(simpleObjects).crear(null, "Faz", null, null, null, null, null, null, null, null, null);

            // then
            final List<Jugador> all = wrap(simpleObjects).listarTodosLosJugadores();
            assertThat(all).hasSize(1);
        }

        @Test
        public void whenAlreadyExists() throws Exception {

            // given
            FixtureScript fs = new SimpleObjectsTearDown();
            fixtureScripts.runFixtureScript(fs, null);
            nextTransaction();
            wrap(simpleObjects).crear(null, "Faz", null, null, null, null, null, null, null, null, null);
            nextTransaction();

            // then
            expectedExceptions.expectCause(causalChainContains(SQLIntegrityConstraintViolationException.class));

            // when
            wrap(simpleObjects).crear(null, "Faz", null, null, null, null, null, null, null, null, null);
            nextTransaction();
        }

        private static Matcher<? extends Throwable> causalChainContains(final Class<?> cls) {
            return new TypeSafeMatcher<Throwable>() {
                @Override
                protected boolean matchesSafely(Throwable item) {
                    final List<Throwable> causalChain = Throwables.getCausalChain(item);
                    for (Throwable throwable : causalChain) {
                        if(cls.isAssignableFrom(throwable.getClass())){
                            return true;
                        }
                    }
                    return false;
                }

                @Override
                public void describeTo(Description description) {
                    description.appendText("exception with causal chain containing " + cls.getSimpleName());
                }
            };
        }
    }

}