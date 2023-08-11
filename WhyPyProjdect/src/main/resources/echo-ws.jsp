<!--웹 소켓 핸들러-->
<websocket:handlers allowed-origins= "http://locallhost:8080">
    <websocket:mapping handler="echoHandler" path="/echo/">
        <websocket:handshake-interceptors>
            <beans:bean class="org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor"/>
        </websocket:handshake-interceptors>
        <websocket:sockjs />
    </websocket:mapping>
</websocket:handlers>
<beans:bean id="echoHandler" class="common.socket.server.EchoHandler"/>