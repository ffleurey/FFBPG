set title 'Evolution of robustness with decentralized adaptation - Red:Smart Blue:Random'
set xlabel 'Adaptation step'
set ylabel 'Robusness index (%)'
set xrange [0:100]
set yrange [45:75]
plot "SMART_0/Simulation_robustness.dat" using 1 notitle with line lc rgb 'red', \
	"SMART_1/Simulation_robustness.dat" using 1 notitle with line lc rgb 'red', \
	"SMART_2/Simulation_robustness.dat" using 1 notitle with line lc rgb 'red', \
	"SMART_3/Simulation_robustness.dat" using 1 notitle with line lc rgb 'red', \
	"SMART_4/Simulation_robustness.dat" using 1 notitle with line lc rgb 'red', \
	"RANDOM_0/Simulation_robustness.dat" using 1 notitle with line lc rgb 'blue', \
	"RANDOM_1/Simulation_robustness.dat" using 1 notitle with line lc rgb 'blue', \
	"RANDOM_2/Simulation_robustness.dat" using 1 notitle with line lc rgb 'blue', \
	"RANDOM_3/Simulation_robustness.dat" using 1 notitle with line lc rgb 'blue', \
	"RANDOM_4/Simulation_robustness.dat" using 1 notitle with line lc rgb 'blue'
