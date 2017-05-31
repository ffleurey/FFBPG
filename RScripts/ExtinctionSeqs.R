library(lattice)

setwd("C:\\home\\checkouts\\DIVERSIFY\\FFBPG\\paper_out\\20160905104108")

# Load the file
mydata <- read.table("Extinctions_Initial.dat", sep="\t", header=FALSE)

# Remove last column
mydata[,(ncol(mydata))] <- NULL


#Plot all columns relative to the first
f <- paste(paste(names(mydata[,-1,drop=FALSE]), collapse="+"),
           names(mydata[,1,drop=FALSE]),
           sep=" ~ ")

pxmax <- mydata[nrow(mydata), 1]
pymax <- mydata[1,2]

xyplot(as.formula(f), data=mydata, type='l', 
       ylab="#Server Killed", xlab="#Client Alive",
       xlim=c(0,pxmax),
       ylim=c(0,pymax),
       main="Extinction sequences"
)

