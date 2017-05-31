set title 'Ratio of unsatisfied requests with multiple server failures'
set xlabel '#Servers Down'
set ylabel 'Unsatisfied Requests (%)'
set xrange [0:24]
plot \
"ExecutionSim.txt" using 3 title 'Initial'  with line lc rgb 'black', \
"CR_SR/ExecutionSim.txt" using 3 title 'Random'  with line lc rgb 'blue', \
"CS_SS/ExecutionSim.txt" using 3 title 'Smart' with lines lw 2 lc rgb 'red'

