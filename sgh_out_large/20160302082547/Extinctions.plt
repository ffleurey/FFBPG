set title 'Average Extinction Sequences'
set xlabel 'SGH Servers Killed'
set ylabel 'SGH Clients Alive'
plot \
"Extinctions_Initial.dat" using 102 title 'Inital' with lines lw 2 lc rgb 'black', \
"CR_SR/Extinctions_Final.dat" using 102 title 'Random' with lines lw 2 lc rgb 'blue', \
"CS_SS/Extinctions_Final.dat" using 102 title 'Smart' with lines lw 2 lc rgb 'red', \

