/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pv243.peaktogether.security;

import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.model.*;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 * This startup bean creates a number of default users, groups and roles when the application is started.
 *
 * @author Shane Bryzak
 */
@Singleton
@Startup
public class IDMInitializer {

    @Inject
    private IdentityManager identityManager;

    @PostConstruct
    public void create() {

        if(identityManager.getGroup("user")==null) {
            SimpleGroup user = new SimpleGroup("user");
            identityManager.add(user);
        }
    }
}
