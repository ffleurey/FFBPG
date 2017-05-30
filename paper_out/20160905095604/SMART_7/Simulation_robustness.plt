set title 'Simulation: Robustness index for 51 runs (avg = 65.28513071895425)'
set xlabel 'Run'
set ylabel 'Robusness index (%)'
set xrange [0:50]
set yrange [45:75]
plot "Simulation_robustness.dat" using 1 notitle with line

