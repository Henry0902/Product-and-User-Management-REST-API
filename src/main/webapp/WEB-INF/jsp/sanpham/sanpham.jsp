<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../layout/header.jsp"%>
<%@include file="../layout/js.jsp"%>

<div class="content-wrapper">
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1>Danh sách sản phẩm</h1>
				</div>
				<div class="col-sm-6">
					<ol class="breadcrumb float-sm-right">
						<li class="breadcrumb-item">
							<a href="${contextPath}/">Trang chủ</a>
						</li>
						<li class="breadcrumb-item active">Danh sách sản phẩm</li>
					</ol>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>

	<form id="submitForm" action="" method="get">
		<section class="content">
			<div class="container-fluid">
				<div class="row clearfix">
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
						<div class="card">
							<div class="card-header">
								<h3 class="card-title">
									<button class="btn btn-block btn-primary btn-xs"
                                            data-toggle="modal" data-target="#largeModal" type="button"
                                            onclick="loadAdd('${contextPath}/api/san-pham/them-moi')">
                                            <i class="fa fa-plus" aria-hidden="true"></i>
                                            
                                        <span>Thêm mới</span>
                                    </button>
                                </h3>

								<div class="card-tools">
									<div class="input-group input-group-sm">
										<input class="form-control form-control-sm" type="text" value="${s_pname}" name="s_pname" placeholder="Tên sản phẩm" data-toggle="tooltip" title="Tên sản phẩm"/>
<%--										<input class="form-control form-control-sm" type="text" value="${s_pdesc}" name="s_pdesc" placeholder="Mô tả" data-toggle="tooltip" title="Mô tả"/>--%>
										<input class="form-control form-control-sm" type="text" value="${s_porigin}" name="s_porigin" placeholder="Nguồn gốc xuất xứ" data-toggle="tooltip" title="Nguồn gốc xuất xứ"/>
<%--										<input class="form-control form-control-sm" type="text" value="${s_pdate}" name="s_fname" placeholder="Năm sản xuất" data-toggle="tooltip" title="Năm sản xuất"/>--%>

										<select class="form-control form-control-sm" name="s_status" data-toggle="tooltip" title="Trạng thái">
											<option value="" ${empty s_status ? 'selected' : ''}>Tất cả</option>
											<option value="0" ${s_status eq '0' ? 'selected': ''}>Không hoạt động</option>
											<option value="1" ${s_status eq '1' ? 'selected': ''}>Hoạt động</option>
										</select>
										<div class="input-group-append">
											<button type="submit" class="btn btn-default">
												<i class="fas fa-search"></i>
											</button>
										</div>
									</div>
								</div>
							</div>
							<div class="card-body table-responsive p-0">
								<table class="table table-bordered table-sm table-striped" id="productTable" >
									<thead>
										<tr>
											<th style="width: 50px;">#</th>
											<th>Tên sản phẩm</th>
											<th>Mô tả</th>
											<th>Nguồn gốc xuất xứ</th>
											<th>Năm sản xuất</th>
											<th style="width: 180px;">Trạng thái</th>
											<th style="width: 100px;"></th>
										</tr>
									</thead>
									<tbody>
<%--										<c:forEach items="${productInfos}" var="item" varStatus="status">--%>
<%--											<tr>--%>
<%--												<th scope="row">${status.index+1 }</th>--%>
<%--												<td>${item.productName }</td>--%>
<%--												<td>${item.productDesc }</td>--%>
<%--												<td>${item.productOrigin }</td>--%>
<%--												<td>${item.productDate }</td>--%>
<%--												<td>--%>
<%--													<c:if test="${item.status eq 1 }">--%>
<%--														<span class="text-primary">Hoạt động</span>--%>
<%--													</c:if>--%>
<%--													<c:if test="${item.status ne 1 }">--%>
<%--														<span class="text-danger">Không hoạt động</span>--%>
<%--													</c:if>--%>
<%--												</td>--%>
<%--												<td class="text-center">--%>
<%--													<a href="javascript:void(0)" onclick="loadEdit('${contextPath}/san-pham/sua?id=${item.id}')" data-toggle="modal" data-target="#largeModal" class="text-info">--%>
<%--														<i class="fa fa-edit"></i>--%>
<%--													</a>--%>
<%--													<a href="javascript:void(0)" onclick="deleteRC('${contextPath}/san-pham/xoa?id=${item.id}')" class="text-danger" style="margin-left: 5px;">--%>
<%--														<i class="fa fa-trash"></i>--%>
<%--													</a>--%>
<%--												</td>--%>
<%--											</tr>--%>
<%--										</c:forEach>--%>
									</tbody>
								</table>

								<%@include file="../layout/paginate.jsp"%>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</form>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		// Gọi API để lấy dữ liệu sản phẩm
		$.ajax({
			url: "${contextPath}/api/sanpham",
			type: "GET",
			dataType: "json",
			success: function(data) {
				// Xóa nội dung hiện tại của bảng
				$("#productTable tbody").empty();

				// Duyệt qua danh sách sản phẩm và thêm vào bảng
				$.each(data, function(index, item) {
					var statusText = item.status == 1 ? "Hoạt động" : "Không hoạt động";
					var statusClass = item.status == 1 ? "text-primary" : "text-danger";

					var row = '<tr>' +
							'<th scope="row">' + (index + 1) + '</th>' +
							'<td>' + item.productName + '</td>' +
							'<td>' + item.productDesc + '</td>' +
							'<td>' + item.productOrigin + '</td>' +
							'<td>' + item.productDate + '</td>' +
							'<td><span class="' + statusClass + '">' + statusText + '</span></td>' +
							'<td class="text-center">' +
							'<a href="javascript:void(0)" onclick="loadEdit(\'' + '${contextPath}/api/san-pham/sua?id=' + item.id + '\')" data-toggle="modal" data-target="#largeModal" class="text-info">' +
							'<i class="fa fa-edit"></i>' +
							'</a>' +
							'<a href="javascript:void(0)" onclick="deleteRC(\'' + '${contextPath}/api/san-pham/xoa?id=' + item.id + '\')" class="text-danger" style="margin-left: 5px;">' +
							'<i class="fa fa-trash"></i>' +
							'</a>' +
							'</td>' +
							'</tr>';

					$("#productTable tbody").append(row);
				});
			},
			error: function() {
				toastr.error("Lỗi khi tải dữ liệu sản phẩm.");
			}
		});
	});
</script>
<%@include file="../layout/footer.jsp"%>