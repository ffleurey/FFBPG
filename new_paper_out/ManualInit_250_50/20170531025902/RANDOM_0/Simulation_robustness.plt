set title 'Simulation: Robustness index for 101 runs (avg = 54.924708794408886)'
set xlabel 'Run'
set ylabel 'Robusness index (%)'
set xrange [0:100]
set yrange [45:75]
plot "Simulation_robustness.dat" using 1 notitle with line

