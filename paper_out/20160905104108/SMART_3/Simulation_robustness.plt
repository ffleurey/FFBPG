set title 'Simulation: Robustness index for 51 runs (avg = 68.22979616212285)'
set xlabel 'Run'
set ylabel 'Robusness index (%)'
set xrange [0:50]
set yrange [45:75]
plot "Simulation_robustness.dat" using 1 notitle with line

