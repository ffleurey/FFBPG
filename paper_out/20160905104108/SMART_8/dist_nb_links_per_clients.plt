set title 'dist_nb_links_per_clients (AVG = 2.919028340080972)'
set xlabel '# Links (to Servers)'
set ylabel '# Clients'
set xrange [0:246]
plot "dist_nb_links_per_clients.dat" using 1 notitle with line

