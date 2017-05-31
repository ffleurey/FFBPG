set title 'Evolution of the robustness index'
set xlabel 'Run'
set ylabel 'Robusness Index (%)'
set xrange [0:50]
set yrange [45:75]
plot \
"CR_SR/Simulation_robustness.dat" using 1 title 'Random'  with line lc rgb 'blue', \
"CS_SS/Simulation_robustness.dat" using 1 title 'Smart' with lines lw 2 lc rgb 'red'

