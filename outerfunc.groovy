def outerTest(message){
	echo message
}

def compress(dirName, outName){
	new AntBuilder().zip(destFile:outName, destdir:dirName)	
}

return this