set title 'dist_nb_links_per_servers (AVG = 0.20491803278688525)'
set xlabel '# Links (from Clients)'
set ylabel '# Servers'
set xrange [0:243]
plot "dist_nb_links_per_servers.dat" using 1 notitle with line

