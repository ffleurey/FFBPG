set grid
set key top left
set xlabel '# Servers Failures'
set ylabel '% Unsatisfied Requests'
plot \
"ExecutionSim.txt" using 1:3 title 'Initial' with lines lw 2  lc rgb 'black', \
"CR_SR/ExecutionSim.txt" using 1:3 title 'Random Baseline' with lines  lw 2  lc rgb 'blue', \
"CS_SS/ExecutionSim.txt" using 1:3 title 'Equitable/Popular' with lines  lw 2  lc rgb 'red'

