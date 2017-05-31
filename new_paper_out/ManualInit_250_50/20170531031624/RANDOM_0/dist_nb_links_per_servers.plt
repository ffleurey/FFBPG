set title 'dist_nb_links_per_servers (AVG = 0.23255813953488372)'
set xlabel '# Links (from Clients)'
set ylabel '# Servers'
set xrange [0:214]
plot "dist_nb_links_per_servers.dat" using 1 notitle with line

