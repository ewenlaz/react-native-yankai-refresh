'use strict';

var React = require('React');
var RCTUIManager = require('NativeModules').UIManager;

var {
	requireNativeComponent,
	View
} = require('react-native');

var TimerMixin = require('react-timer-mixin');


var PullToRefreshView = requireNativeComponent('yankai-react-native-pull-to-refresh-view');

var {
	Log
} = require('react-native-yankai-utils');

var Commands = {
	END_REFRESH: 1
}

var PropTypes = React.PropTypes;

module.exports = React.createClass({

	mixins: [
		TimerMixin
	],

	propTypes: {
		size: PropTypes.oneOf(['default', 'large']),
		colors: PropTypes.string,
		renderContent: PropTypes.func.isRequired,
		onRefresh: PropTypes.func,
		timeout: PropTypes.number,
		onTimeout: PropTypes.func,
	},

	getInitialState: function() {
		return {
			hasInit: false,
			contentViewHeight: 0,
		}
	},

	getDefaultProps: function() {
		return {
			size: 'default',
			timeout: 1000 * 30,
			onRefresh: () => {},
			onTimeout: () => {},
		}
	},

	componentWillUnmount: function() {
		if (this._timeout_id > 0) {
			this.clearTimeout(this._timeout_id);
		}
	},

	render: function() {

		var content = null;

		if (this.state.hasInit) {

			let contentView = React.Children.only(this.props.children);

			let style = [contentView.props.style, {
				'flex': 1,
				'height': this.state.contentViewHeight,
			}];

			content = React.cloneElement(contentView, {
				'style': style
			});
		}

		var containerStyle = {
			flex: 1
		};

		return (
			<View style={containerStyle} onLayout={this._calcContainerHeight}>
				<PullToRefreshView 
					ref="PULL_TO_REFRESH_VIEW"
					size={this.props.size}
					colors={this.props.colors}
					onRefresh={this._onRefresh}>
					{content}
				</PullToRefreshView>
			</View>
		)
	},

	endRefresh: function() {
		this._endRefresh();
	},

	_calcContainerHeight: function(e) {
		var height = e.nativeEvent.layout.height;
		if (!this.state.hasInit && height > 0) {
			this.setState({
				contentViewHeight: height,
				hasInit: true,
			});
		}
	},

	_onRefresh: function() {
		Log.info(this.props.onRefresh.toString());

		this._timeout_id = this.setTimeout(this._timeout, this.props.timeout);

		this.props.onRefresh();

		Log
	},

	_timeout: function() {
		this._endRefresh();
		this.props.onTimeout();
	},

	_endRefresh: function() {

		if (this._timeout_id > 0) {
			this.clearTimeout(this._timeout_id);
			this._timeout_id = -1;
		}

		var handle = React.findNodeHandle(this.refs['PULL_TO_REFRESH_VIEW']);

		RCTUIManager.dispatchViewManagerCommand(
			handle,
			Commands.END_REFRESH,
			null
		);
	}
});