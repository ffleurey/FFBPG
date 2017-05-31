set title 'dist_nb_links_per_servers (AVG = 0.22123893805309736)'
set xlabel '# Links (from Clients)'
set ylabel '# Servers'
set xrange [0:2259]
plot "dist_nb_links_per_servers.dat" using 1 notitle with line

