setwd("C:\\home\\checkouts\\DIVERSIFY\\FFBPG\\paper_out\\20160905104108")

# Load the file
simdata <- read.table("ExecutionSim.txt", sep="\t", header=FALSE)

colnames(simdata) <- c("srv_down","srv_tot", "failrate", "fail", "reqs")

#pxmax <- mydata[nrow(mydata), 1]
#pymax <- mydata[1,2]

xyplot(failrate~srv_down, data=simdata, type='o', grid = TRUE)

directories <- list.files(pattern="RANDOM*")

for (i in i:length(directories)) {
  
  
}