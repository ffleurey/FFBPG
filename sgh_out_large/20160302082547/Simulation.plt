set title 'Unsatisfied requests with multiple server failures'
set xlabel '# Servers Failures'
set ylabel '% Unsatisfied Requests'
set xrange [0:20]
plot \
"ExecutionSim.txt" using 1:3 title 'Initial' with linespoints pt 1 ps 1 lc rgb 'black', \
"CR_SR/ExecutionSim.txt" using 1:3 title 'Random' with linespoints pt 2 ps 1 lc rgb 'blue', \
"CS_SS/ExecutionSim.txt" using 1:3 title 'Smart' with linespoints pt 3 ps 1 lc rgb 'red'

