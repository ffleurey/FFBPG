set title 'dist_nb_links_per_servers (AVG = 0.3275058275058275)'
set xlabel '# Links (from Clients)'
set ylabel '# Servers'
set xrange [0:2573]
plot "dist_nb_links_per_servers.dat" using 1 notitle with line

