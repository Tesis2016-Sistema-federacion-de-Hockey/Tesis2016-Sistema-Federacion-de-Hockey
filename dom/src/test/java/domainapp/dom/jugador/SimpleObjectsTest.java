/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package domainapp.dom.jugador;

import java.util.List;

import com.google.common.collect.Lists;

import domainapp.dom.jugador.Jugador;
import domainapp.dom.jugador.JugadorServicio;

import org.jmock.Expectations;
import org.jmock.Sequence;
import org.jmock.auto.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2.Mode;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleObjectsTest {

    @Rule
    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(Mode.INTERFACES_AND_CLASSES);

    @Mock
    RepositoryService mockRepositoryService;
    
    JugadorServicio simpleObjects;

    @Before
    public void setUp() throws Exception {
        simpleObjects = new JugadorServicio();
        simpleObjects.repositoryService = mockRepositoryService;
    }

    public static class Create extends SimpleObjectsTest {

        @Test
        public void happyCase() throws Exception {

            // given
            final Jugador simpleObject = new Jugador();

            final Sequence seq = context.sequence("create");
            context.checking(new Expectations() {
                {
                    oneOf(mockRepositoryService).instantiate(Jugador.class);
                    inSequence(seq);
                    will(returnValue(simpleObject));

                    oneOf(mockRepositoryService).persist(simpleObject);
                    inSequence(seq);
                }
            });

            // when
//            final Jugador obj = simpleObjects.crear(null, "Nombre 1","Apellido 01", null, null, null, null, null, null, null, 0, 0, null, null, null);

            // then
//            assertThat(obj).isEqualTo(simpleObject);
//            assertThat(obj.getNombre()).isEqualTo("Foobar");
        }

    }

    public static class ListAll extends SimpleObjectsTest {

        @Test
        public void happyCase() throws Exception {

            // given
            final List<Jugador> all = Lists.newArrayList();

            context.checking(new Expectations() {
                {
                    oneOf(mockRepositoryService).allInstances(Jugador.class);
                    will(returnValue(all));
                }
            });

            // when
            final List<Jugador> list = simpleObjects.listarTodosLosJugadores();

            // then
            assertThat(list).isEqualTo(all);
        }
    }
}
