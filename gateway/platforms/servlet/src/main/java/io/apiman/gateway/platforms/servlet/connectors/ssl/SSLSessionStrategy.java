/*
 * Copyright 2015 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.apiman.gateway.platforms.servlet.connectors.ssl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

/**
 * Encapsulates basic SSL strategy information
 *
 * @author Marc Savy <msavy@redhat.com>
 */
public class SSLSessionStrategy {

    private HostnameVerifier hostnameVerifier;
    private SSLSocketFactory socketFactory;

    /**
     * Construct an {@link SSLSessionStrategy}
     *
     * @param hostnameVerifier the hostname verifier
     * @param socketFactory the socket factory
     */
    public SSLSessionStrategy(HostnameVerifier hostnameVerifier,
            SSLSocketFactory socketFactory) {
        this.hostnameVerifier = hostnameVerifier;
        this.socketFactory = socketFactory;
    }

    /**
     * @return the hostnameVerifier
     */
    public HostnameVerifier getHostnameVerifier() {
        return hostnameVerifier;
    }

    /**
     * @return the socket factory
     */
    public SSLSocketFactory getSocketFactory() {
        return socketFactory;
    }

}
