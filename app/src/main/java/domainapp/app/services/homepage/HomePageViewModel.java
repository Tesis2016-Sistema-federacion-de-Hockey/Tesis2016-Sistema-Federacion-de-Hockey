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
package domainapp.app.services.homepage;

import java.util.List;

import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.ViewModel;

import domainapp.dom.club.Club;
import domainapp.dom.club.ClubServicio;
import domainapp.dom.division.Division;
import domainapp.dom.division.DivisionServicio;
import domainapp.dom.jugador.Jugador;
import domainapp.dom.jugador.JugadorServicio;
import domainapp.dom.temporada.Temporada;
import domainapp.dom.temporada.TemporadaServicio;
import domainapp.dom.torneo.Torneo;
import domainapp.dom.torneo.TorneoServicio;

@ViewModel
public class HomePageViewModel {

    public String title() {
        return "Federacion Neuquina de Hockey";
    }
    
    @HomePage()
    @CollectionLayout(named="Temporadas Activas")
    public List<Temporada> getTemporadas() {
        return temporadaServicio.listarTemporadasActivas();
    }

    @HomePage
    @CollectionLayout(named="Torneos Activos")
    public List<Torneo> getTorneos() {
        return torneoServicio.listarTorneosActivos();
    }
    
    @HomePage
    @CollectionLayout(named="Divisiones Activas")
    public List<Division> getDivisiones() {
        return divisionServicio.listarDivisionesActivas();
    }
    
    @HomePage
    @CollectionLayout(named="Clubes")
    public List<Club> getClubes() {
        return clubServicio.listarTodosLosClubes();
    }
    
    @HomePage
    @CollectionLayout(named="Jugadores")
    public List<Jugador> getJugadores() {
        return jugadorServicio.listarJugadoresActivos();
    }

    @javax.inject.Inject
    JugadorServicio jugadorServicio;
    
    @javax.inject.Inject
    ClubServicio clubServicio;
    
    @javax.inject.Inject
    DivisionServicio divisionServicio;
    
    @javax.inject.Inject
    TemporadaServicio temporadaServicio;
    
    @javax.inject.Inject
    TorneoServicio torneoServicio;
}