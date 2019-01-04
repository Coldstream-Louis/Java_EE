function showData2 (nodes, links) {
    var colorMap = new Map();
    colorMap.set("user",'aqua');
    colorMap.set("position",'lightskyblue');
    colorMap.set("project",'deepskyblue');
    var N = JSON.stringify(nodes)
    var E = JSON.stringify(links)
    localStorage.setItem('nodes', N)
    localStorage.setItem('links', E)
    var width = 1200
    var height = 600
    var has_graph = false
    var svg = d3.select('svg'),
        width = +svg.attr('width'),
        height = +svg.attr('height')
    svg.selectAll('*').remove()
    console.log(d3.forceManyBody())
    // 通过布局来转换数据，然后进行绘制
    var simulation = d3.forceSimulation(nodes)
        .force('link', d3.forceLink(links).id(function (d) { return d.id }))
        .force('charge', d3.forceManyBody()) // 创建多体力
        .force('center', d3.forceCenter(width / 2, height / 2))

    d3.select('input[type = range]')
        .on('input', inputted)

    simulation
        .nodes(nodes)// 设置力模拟的节点
        .on('tick', ticked)

    simulation.force('link').strength(0.06)// 添加或移除力
        .links(links)// 设置连接数组
    var color = d3.scaleOrdinal(d3.schemeCategory20)
    // 绘制
    var svg_links = svg.selectAll('line')
        .data(links)
        .enter()
        .append('line')
        .style('stroke', '#ccc')
        .style('stroke-width', 1)
        .call(d3.zoom()// 创建缩放行为
            .scaleExtent([-5, 2])// 设置缩放范围
        )

    var svg_nodes = svg.selectAll('circle')
        .data(nodes)
        .enter()
        .append('circle')
        .attr('cx', function (d) { return d.x })
        .attr('cy', function (d) { return d.y })
        .attr('r', '16')
        .attr('fill', function (d) { return colorMap.get(d.type) })
        .attr('stroke', 'gray')
        .style('stroke-width', 2)
        .call(d3.drag()
            .on('start', dragstarted)
            .on('drag', dragged)
            .on('end', dragended))

    var svg_texts = svg.selectAll('text')
        .data(nodes)
        .enter()
        .append('text')
        .style('fill', 'black')
        .attr('dx', 1)
        .attr('dy', 1)
        .attr('text-anchor', 'middle')
        .attr('font-size', 10)
        .text(function (d) {
            return d.name.substring(0,8)
        })

    function dragstarted (d) {
        if (!d3.event.active) simulation.alphaTarget(0.3).restart()// 设置目标α
        d.fx = d.x
        d.fy = d.y
    }

    function dragged (d) {
        d.fx = d3.event.x
        d.fy = d3.event.y
    }

    function dragended (d) {
        if (!d3.event.active) simulation.alphaTarget(0)
        d.fx = null
        d.fy = null
    }

    function inputted () {
        simulation.force('link').strength(+this.value)
        simulation.alpha(1).restart();
    }

    function ticked () {
        svg_links.attr('x1', function (d) { return d.source.x })
            .attr('y1', function (d) { return d.source.y })
            .attr('x2', function (d) { return d.target.x })
            .attr('y2', function (d) { return d.target.y })

        svg_nodes.attr('cx', function (d) { return d.x })
            .attr('cy', function (d) { return d.y })

        svg_texts.attr('x', function (d) { return d.x })
            .attr('y', function (d) { return d.y })

    }
}

export { showData2 }