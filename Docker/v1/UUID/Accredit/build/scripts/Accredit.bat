@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  Accredit startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Add default JVM options here. You can also use JAVA_OPTS and ACCREDIT_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args
if "%@eval[2+2]" == "4" goto 4NT_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*
goto execute

:4NT_args
@rem Get arguments from the 4NT Shell from JP Software
set CMD_LINE_ARGS=%$

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\Accredit-0.0.1-SNAPSHOT.jar;%APP_HOME%\lib\log4j-api-2.8.2.jar;%APP_HOME%\lib\log4j-core-2.8.2.jar;%APP_HOME%\lib\httpclient-4.5.1.jar;%APP_HOME%\lib\spring-boot-starter-web-1.2.0.RELEASE.jar;%APP_HOME%\lib\springfox-swagger2-2.0.2.jar;%APP_HOME%\lib\springfox-swagger-ui-2.0.2.jar;%APP_HOME%\lib\commons-codec-1.10.jar;%APP_HOME%\lib\spring-boot-starter-actuator-1.2.0.RELEASE.jar;%APP_HOME%\lib\spring-boot-starter-data-mongodb-1.2.0.RELEASE.jar;%APP_HOME%\lib\spring-boot-starter-data-rest-1.2.0.RELEASE.jar;%APP_HOME%\lib\commons-io-1.3.2.jar;%APP_HOME%\lib\commons-lang-2.6.jar;%APP_HOME%\lib\grpc-all-0.13.2.jar;%APP_HOME%\lib\commons-cli-1.3.1.jar;%APP_HOME%\lib\fabric-sdk-java-0.6.jar;%APP_HOME%\lib\hyperledger-java-client-0.1.3.jar;%APP_HOME%\lib\logging-interceptor-3.6.0.jar;%APP_HOME%\lib\org.eclipse.paho.client.mqttv3-1.1.1.jar;%APP_HOME%\lib\javassist-3.8.0.GA.jar;%APP_HOME%\lib\docker-client-8.8.0.jar;%APP_HOME%\lib\sgf4j-parser-0.0.1-SNAPSHOT.jar;%APP_HOME%\lib\rx-docker-client-0.2.2.jar;%APP_HOME%\lib\spring-webmvc-4.3.8.RELEASE.jar;%APP_HOME%\lib\spring-boot-starter-1.2.0.RELEASE.jar;%APP_HOME%\lib\spring-boot-starter-tomcat-1.2.0.RELEASE.jar;%APP_HOME%\lib\hibernate-validator-5.1.3.Final.jar;%APP_HOME%\lib\mapstruct-1.0.0.Beta4.jar;%APP_HOME%\lib\swagger-annotations-1.5.0.jar;%APP_HOME%\lib\swagger-models-1.5.0.jar;%APP_HOME%\lib\springfox-spi-2.0.2.jar;%APP_HOME%\lib\springfox-schema-2.0.2.jar;%APP_HOME%\lib\springfox-swagger-common-2.0.2.jar;%APP_HOME%\lib\springfox-spring-web-2.0.2.jar;%APP_HOME%\lib\guava-18.0.jar;%APP_HOME%\lib\classmate-1.2.0.jar;%APP_HOME%\lib\joda-time-2.8.jar;%APP_HOME%\lib\spring-plugin-core-1.2.0.RELEASE.jar;%APP_HOME%\lib\spring-plugin-metadata-1.2.0.RELEASE.jar;%APP_HOME%\lib\spring-hateoas-0.17.0.RELEASE.jar;%APP_HOME%\lib\spring-boot-actuator-1.2.0.RELEASE.jar;%APP_HOME%\lib\mongo-java-driver-2.12.4.jar;%APP_HOME%\lib\spring-tx-4.1.3.RELEASE.jar;%APP_HOME%\lib\spring-data-mongodb-1.6.1.RELEASE.jar;%APP_HOME%\lib\spring-data-rest-webmvc-2.2.1.RELEASE.jar;%APP_HOME%\lib\grpc-netty-0.13.2.jar;%APP_HOME%\lib\grpc-auth-0.13.2.jar;%APP_HOME%\lib\grpc-protobuf-nano-0.13.2.jar;%APP_HOME%\lib\grpc-core-0.13.2.jar;%APP_HOME%\lib\grpc-protobuf-0.13.2.jar;%APP_HOME%\lib\grpc-okhttp-0.13.2.jar;%APP_HOME%\lib\grpc-stub-0.13.2.jar;%APP_HOME%\lib\protobuf-java-3.0.2.jar;%APP_HOME%\lib\bcprov-jdk15on-1.55.jar;%APP_HOME%\lib\commons-compress-1.12.jar;%APP_HOME%\lib\retrofit-2.0.2.jar;%APP_HOME%\lib\converter-gson-2.0.2.jar;%APP_HOME%\lib\adapter-rxjava-2.0.2.jar;%APP_HOME%\lib\jackson-jaxrs-json-provider-2.8.8.jar;%APP_HOME%\lib\jackson-datatype-guava-2.8.8.jar;%APP_HOME%\lib\jersey-client-2.22.2.jar;%APP_HOME%\lib\jersey-apache-connector-2.22.2.jar;%APP_HOME%\lib\jersey-media-json-jackson-2.22.2.jar;%APP_HOME%\lib\jnr-unixsocket-0.8.jar;%APP_HOME%\lib\bcpkix-jdk15on-1.52.jar;%APP_HOME%\lib\logback-classic-1.2.1.jar;%APP_HOME%\lib\zt-zip-1.5.jar;%APP_HOME%\lib\log4j-slf4j-impl-2.7.jar;%APP_HOME%\lib\rx-okhttp-0.1.9.jar;%APP_HOME%\lib\gson-2.6.2.jar;%APP_HOME%\lib\spring-boot-1.2.0.RELEASE.jar;%APP_HOME%\lib\spring-boot-autoconfigure-1.2.0.RELEASE.jar;%APP_HOME%\lib\spring-boot-starter-logging-1.2.0.RELEASE.jar;%APP_HOME%\lib\snakeyaml-1.14.jar;%APP_HOME%\lib\tomcat-embed-core-8.0.15.jar;%APP_HOME%\lib\tomcat-embed-el-8.0.15.jar;%APP_HOME%\lib\tomcat-embed-logging-juli-8.0.15.jar;%APP_HOME%\lib\tomcat-embed-websocket-8.0.15.jar;%APP_HOME%\lib\validation-api-1.1.0.Final.jar;%APP_HOME%\lib\jboss-logging-3.1.3.GA.jar;%APP_HOME%\lib\springfox-core-2.0.2.jar;%APP_HOME%\lib\objenesis-2.1.jar;%APP_HOME%\lib\spring-data-commons-1.9.1.RELEASE.jar;%APP_HOME%\lib\jcl-over-slf4j-1.7.7.jar;%APP_HOME%\lib\spring-data-rest-core-2.2.1.RELEASE.jar;%APP_HOME%\lib\json-patch-1.7.jar;%APP_HOME%\lib\netty-codec-http2-4.1.0.CR3.jar;%APP_HOME%\lib\google-auth-library-oauth2-http-0.3.0.jar;%APP_HOME%\lib\protobuf-javanano-3.0.0-alpha-5.jar;%APP_HOME%\lib\jsr305-3.0.0.jar;%APP_HOME%\lib\okhttp-2.5.0.jar;%APP_HOME%\lib\rxjava-1.1.1.jar;%APP_HOME%\lib\jackson-jaxrs-base-2.8.8.jar;%APP_HOME%\lib\jackson-core-2.8.8.jar;%APP_HOME%\lib\jackson-module-jaxb-annotations-2.8.8.jar;%APP_HOME%\lib\javax.ws.rs-api-2.0.1.jar;%APP_HOME%\lib\jersey-common-2.22.2.jar;%APP_HOME%\lib\hk2-api-2.4.0-b34.jar;%APP_HOME%\lib\javax.inject-2.4.0-b34.jar;%APP_HOME%\lib\hk2-locator-2.4.0-b34.jar;%APP_HOME%\lib\jersey-entity-filtering-2.22.2.jar;%APP_HOME%\lib\jnr-ffi-2.0.3.jar;%APP_HOME%\lib\jnr-constants-0.8.7.jar;%APP_HOME%\lib\jnr-enxio-0.9.jar;%APP_HOME%\lib\jnr-posix-3.0.12.jar;%APP_HOME%\lib\logback-core-1.2.1.jar;%APP_HOME%\lib\junixsocket-common-2.0.4.jar;%APP_HOME%\lib\junixsocket-native-common-2.0.4.jar;%APP_HOME%\lib\jul-to-slf4j-1.7.7.jar;%APP_HOME%\lib\log4j-over-slf4j-1.7.7.jar;%APP_HOME%\lib\evo-inflector-1.2.jar;%APP_HOME%\lib\jackson-coreutils-1.6.jar;%APP_HOME%\lib\netty-codec-http-4.1.0.CR3.jar;%APP_HOME%\lib\netty-handler-4.1.0.CR3.jar;%APP_HOME%\lib\google-auth-library-credentials-0.3.0.jar;%APP_HOME%\lib\google-http-client-1.19.0.jar;%APP_HOME%\lib\google-http-client-jackson2-1.19.0.jar;%APP_HOME%\lib\javax.annotation-api-1.2.jar;%APP_HOME%\lib\jersey-guava-2.22.2.jar;%APP_HOME%\lib\osgi-resource-locator-1.0.1.jar;%APP_HOME%\lib\hk2-utils-2.4.0-b34.jar;%APP_HOME%\lib\aopalliance-repackaged-2.4.0-b34.jar;%APP_HOME%\lib\javassist-3.18.1-GA.jar;%APP_HOME%\lib\jffi-1.2.9.jar;%APP_HOME%\lib\jffi-1.2.9-native.jar;%APP_HOME%\lib\asm-5.0.3.jar;%APP_HOME%\lib\asm-commons-5.0.3.jar;%APP_HOME%\lib\asm-analysis-5.0.3.jar;%APP_HOME%\lib\asm-tree-5.0.3.jar;%APP_HOME%\lib\asm-util-5.0.3.jar;%APP_HOME%\lib\jnr-x86asm-1.0.2.jar;%APP_HOME%\lib\log4j-1.2.17.jar;%APP_HOME%\lib\native-lib-loader-2.0.2.jar;%APP_HOME%\lib\msg-simple-1.1.jar;%APP_HOME%\lib\netty-codec-4.1.0.CR3.jar;%APP_HOME%\lib\netty-buffer-4.1.0.CR3.jar;%APP_HOME%\lib\netty-transport-4.1.0.CR3.jar;%APP_HOME%\lib\javax.inject-1.jar;%APP_HOME%\lib\btf-1.2.jar;%APP_HOME%\lib\netty-common-4.1.0.CR3.jar;%APP_HOME%\lib\netty-resolver-4.1.0.CR3.jar;%APP_HOME%\lib\commons-io-2.5.jar;%APP_HOME%\lib\slf4j-api-1.7.22.jar;%APP_HOME%\lib\jackson-databind-2.8.8.jar;%APP_HOME%\lib\httpcore-4.4.5.jar;%APP_HOME%\lib\spring-context-4.3.8.RELEASE.jar;%APP_HOME%\lib\jackson-annotations-2.8.0.jar;%APP_HOME%\lib\spring-beans-4.3.8.RELEASE.jar;%APP_HOME%\lib\spring-aop-4.3.8.RELEASE.jar;%APP_HOME%\lib\spring-core-4.3.8.RELEASE.jar;%APP_HOME%\lib\spring-web-4.3.8.RELEASE.jar;%APP_HOME%\lib\okhttp-3.6.0.jar;%APP_HOME%\lib\spring-expression-4.3.8.RELEASE.jar;%APP_HOME%\lib\commons-logging-1.2.jar;%APP_HOME%\lib\okio-1.11.0.jar

@rem Execute Accredit
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %ACCREDIT_OPTS%  -classpath "%CLASSPATH%" info.smartkit.UUIDAccreditApplication %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable ACCREDIT_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%ACCREDIT_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
