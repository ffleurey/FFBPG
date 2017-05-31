set title 'Average Extinction Sequences'
set grid
set xlabel 'SGH Servers Killed'
set ylabel 'SGH Clients Alive'
plot \
"Extinctions_Initial.dat" using 122 title 'Inital' with lines lw 2 lc rgb 'black', \
"CR_SR/Extinctions_Final.dat" using 122 title 'Random Baseline' with lines lw 2 lc rgb 'blue', \
"CS_SS/Extinctions_Final.dat" using 122 title 'Equitable/Popular' with lines lw 2 lc rgb 'red', \

