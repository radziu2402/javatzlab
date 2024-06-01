keytool -genkeypair -alias mykey -keyalg RSA -keystore mykeystore.jks -validity 365 -keysize 2048

jarsigner -keystore mykeystore.jks -storepass password -keypass password -signedjar signed-lab10-biblioteka-1.0-SNAPSHOT.jar target/lab10-biblioteka-1.0-SNAPSHOT.jar mykey
